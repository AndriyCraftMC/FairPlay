package FairPlay.detections.combat.killaura;

import FairPlay.log.Logger;
import FairPlay.utils.NPC;
import FairPlay.utils.PacketHandler;
import com.mojang.authlib.GameProfile;
import io.netty.channel.Channel;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public class KillauraA implements Listener {
    public static ArrayList<Player> killauravl = new ArrayList<>();
    public static ArrayList<Player> nonpcs = new ArrayList<>();
    public static ArrayList<String> hits = new ArrayList<>();

    public static void generateNPC(EntityDamageByEntityEvent e) {
        try {
            for (int i = 0; i < hits.size(); i++) {
                hits.remove(((Player) e.getDamager()).getDisplayName());
            }

            Player player = (Player) e.getDamager();

            if (nonpcs.contains(player)) {
                return;
            }
            nonpcs.add(player);

            Logger.log("Spawned NPC for " + ((Player) e.getDamager()).getDisplayName());

            String name;

            List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
            if (players.size() > 0) {
                int randomIndex = new Random().nextInt(players.size());
                Player randomPlayer = players.get(randomIndex);
                name = randomPlayer.getName();
            } else {
                throw new Exception("This error should never happen. If this somehow happened - you won");
            }

            MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
            WorldServer world = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
            UUID uuid = UUID.fromString(String.valueOf(UUID.randomUUID()));
            EntityPlayer npc = new EntityPlayer(server, world,
                    new GameProfile(uuid, name),
                    new PlayerInteractManager(world));


            npc.setLocation(player.getLocation().getX(), player.getLocation().getY() - 10, player.getLocation().getZ(), 0, 0);

            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));

            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));

            NPC.updateArmor(connection, npc);
            PacketHandler.setupHandler(player, npc, connection);
        } catch (Exception error) {
            Logger.log("[error] Killaura error: " + error);
        }
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
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player p = (Player) e.getDamager();

            hits.add(p.getDisplayName());
            int s = 0;
            for (int i = 0; i < hits.size(); i++) {
                if (Objects.equals(p.getDisplayName(), p.getPlayer().getDisplayName())) ++s;
            }

            if (s > 5) generateNPC(e);
        }
    }
}
