package FairPlay.utils;

import FairPlay.data.Punishment;
import FairPlay.log.Flag;
import FairPlay.log.Logger;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Objects;

import static FairPlay.detections.combat.killaura.KillauraA.hits;
import static FairPlay.detections.combat.killaura.KillauraA.killauravl;
import static FairPlay.utils.GetPlayerRotation.getRotation;


public class PacketHandler {
    public static void setupHandler(Player player, EntityPlayer npc, PlayerConnection connection) {
        Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("FairPlay"), new Runnable() {
            public void run() {
                Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
                channel.eventLoop().submit(() -> {
                    channel.pipeline().remove(player.getName());
                    return null;
                });
                FairPlay.detections.combat.killaura.KillauraA.nonpcs.remove(player);
                connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getBukkitEntity().getEntityId()));
                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
            }
        }, 100L);


        Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("FairPlay"), new Runnable() {
            public void run() {
                npc.yaw = getRotation(npc.getBukkitEntity().getLocation(), player.getLocation());
                Location playerLoc = player.getLocation();
                Location npcLoc = playerLoc.add(playerLoc.getDirection().multiply((double) ((int) (Math.random() * -5 - 1))));

                if (playerLoc.getPitch() < -60) {
                    npcLoc.setY(npcLoc.getY() - 3);
                } else {
                    npcLoc.setY(npcLoc.getY() + 2);
                }

                npc.setLocation(npcLoc.getX(), npcLoc.getY(), npcLoc.getZ(), npc.yaw, 0);
                connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) ((npc.yaw * 256.0F) / 360.0F)));
                connection.sendPacket(new PacketPlayOutEntityTeleport(npc));
            }
        }, 1L, 1L);


        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
                if (packet instanceof PacketPlayInUseEntity) {
                    Field f = packet.getClass().getDeclaredField("a");
                    f.setAccessible(true);
                    if ((Integer.parseInt(f.get(packet).toString()) == npc.getId())) {
                        Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugin("FairPlay"), new Runnable() {
                            public void run() {
                                killauravl.add(player);
                                int s = 0;
                                for (int i = 0; i < killauravl.size(); i++) {
                                    if (Objects.equals(player.getDisplayName(), player.getDisplayName())) ++s;
                                }
                                Flag.flag("Killaura/A", player, "VL " + s + " | Entity id: " + npc.getId());
                                if (s >= 5) {
                                    for (int i = 0; i < killauravl.size(); i++) {
                                        killauravl.remove(player);
                                    }
                                    hits.clear();
                                    Punishment.punish(player);
                                }
                            }
                        });
                    }
                }
                super.channelRead(channelHandlerContext, packet);
            }
        };

        ChannelPipeline pipeline = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline();
        pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
    }
}