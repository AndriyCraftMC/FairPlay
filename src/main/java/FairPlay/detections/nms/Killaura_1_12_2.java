package FairPlay.detections.nms;

import FairPlay.data.Ban;
import FairPlay.data.UsernameGenerator;
import FairPlay.log.Logger;
import com.mojang.authlib.GameProfile;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.Field;
import java.util.*;

public class Killaura_1_12_2 implements Listener {
    public static ArrayList<Player> killauravl = new ArrayList<>();
    public static ArrayList<Player> nonpcs = new ArrayList<>();

    public static void generateNPC(EntityDamageByEntityEvent e) {
        try {
            Player player = (Player) e.getDamager();

            if (nonpcs.contains(player)) {
                return;
            }
            nonpcs.add(player);

            String name = "§7 z" + UsernameGenerator.generateRandomString();


            MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
            WorldServer world = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
            UUID uuid = UUID.fromString(String.valueOf(UUID.randomUUID()));
            EntityPlayer npc = new EntityPlayer(server, world,
                    new GameProfile(uuid, name),
                    new PlayerInteractManager(world));


            Item[] armorItems1 = {
                    Items.LEATHER_HELMET,
                    Items.CHAINMAIL_HELMET,
                    Items.IRON_HELMET,
                    Items.DIAMOND_HELMET,
                    Items.GOLDEN_HELMET
            };

            Item[] armorItems2 = {
                    Items.CHAINMAIL_CHESTPLATE,
                    Items.DIAMOND_CHESTPLATE,
                    Items.IRON_CHESTPLATE,
                    Items.LEATHER_CHESTPLATE,
                    Items.GOLDEN_CHESTPLATE
            };

            Item[] armorItems3 = {
                    Items.CHAINMAIL_LEGGINGS,
                    Items.LEATHER_LEGGINGS,
                    Items.DIAMOND_LEGGINGS,
                    Items.IRON_LEGGINGS,
                    Items.GOLDEN_LEGGINGS
            };

            Item[] armorItems4 = {
                    Items.CHAINMAIL_BOOTS,
                    Items.DIAMOND_BOOTS,
                    Items.GOLDEN_BOOTS,
                    Items.LEATHER_BOOTS,
                    Items.IRON_BOOTS
            };
            Random random = new Random();

            npc.setSlot(EnumItemSlot.HEAD, new ItemStack(armorItems1[random.nextInt(armorItems1.length)]));
            npc.setSlot(EnumItemSlot.CHEST, new ItemStack(armorItems2[random.nextInt(armorItems2.length)]));
            npc.setSlot(EnumItemSlot.LEGS, new ItemStack(armorItems3[random.nextInt(armorItems3.length)]));
            npc.setSlot(EnumItemSlot.FEET, new ItemStack(armorItems4[random.nextInt(armorItems4.length)]));

            npc.setSlot(EnumItemSlot.MAINHAND, new ItemStack(Items.DIAMOND_AXE));

            npc.setLocation(player.getLocation().getX(), player.getLocation().getY() + 10, player.getLocation().getZ(), 0, 0);

            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("FairPlay"), new Runnable() {
                public void run() {
                    Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
                    channel.eventLoop().submit(() -> {
                        channel.pipeline().remove(player.getName());
                        return null;
                    });
                    nonpcs.remove(player);
                    connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getBukkitEntity().getEntityId()));
                    connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
                }
            }, 300L);

            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));

            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));

            connection.sendPacket(new PacketPlayOutEntityEquipment(npc.getId(), EnumItemSlot.HEAD, npc.getEquipment(EnumItemSlot.HEAD)));
            connection.sendPacket(new PacketPlayOutEntityEquipment(npc.getId(), EnumItemSlot.CHEST, npc.getEquipment(EnumItemSlot.CHEST)));
            connection.sendPacket(new PacketPlayOutEntityEquipment(npc.getId(), EnumItemSlot.LEGS, npc.getEquipment(EnumItemSlot.LEGS)));
            connection.sendPacket(new PacketPlayOutEntityEquipment(npc.getId(), EnumItemSlot.FEET, npc.getEquipment(EnumItemSlot.FEET)));

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
                                    Logger.log(player.getDisplayName() + " failed Killaura | VL " + s + " | Entity id: " + npc.getId() + " | Is authed: " + player.hasPermission("ac.execnpccmds"));
                                    if (s >= 5) {
                                        for (int i = 0; i < killauravl.size(); i++) {
                                            killauravl.remove(player);
                                        }
                                        Ban.ban(player);
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

            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) 0));

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
        } catch (Exception ignored) { }
    }

    public static float getRotation(Location loc, Location target) {
        double xDiff = target.getX() - loc.getX();
        double zDiff = target.getZ() - loc.getZ();
        float yaw = (float) Math.toDegrees(-Math.atan(xDiff / zDiff));
        if (zDiff < 0.0D) {
            yaw += 180.0F;
        }
        return yaw;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Channel channel = ((CraftPlayer) e.getPlayer()).getHandle().playerConnection.networkManager.channel;
        channel.eventLoop().submit(() -> {
            channel.pipeline().remove(e.getPlayer().getName());
            return null;
        });
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            generateNPC(e);
        }
    }
}
