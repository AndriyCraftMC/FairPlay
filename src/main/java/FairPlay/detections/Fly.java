package FairPlay.detections;

import FairPlay.data.Ban;
import FairPlay.log.Logger;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.Objects;

import static FairPlay.data.PingManager.getPing;

public class Fly implements Listener {
    public static ArrayList<Player> flyvl = new ArrayList<>();

    public static ArrayList<Player> flywhitelist = new ArrayList<>();

    public static boolean isSlimeblockNear(Player player) {
        Location playerLocation = player.getLocation();
        World world = player.getWorld();

        int radius = 10;

        for (int x = playerLocation.getBlockX() - radius; x <= playerLocation.getBlockX() + radius; x++) {
            for (int y = 0; y <= 255; y++) {
                for (int z = playerLocation.getBlockZ() - radius; z <= playerLocation.getBlockZ() + radius; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    if (block.getType() == Material.SLIME_BLOCK) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE
            || event.getPlayer().getGameMode() == GameMode.SPECTATOR) {
                return;
        }

        Player player = event.getPlayer();
        int ping = getPing(player);

        double maxmovement;

        if (ping <= 50) {
            maxmovement = 0.7;
        } else if (ping <= 100) {
            maxmovement = 0.8;
        } else if (ping <= 200) {
            maxmovement = 0.9;
        } else if (ping <= 500) {
            maxmovement = 1.0;
        } else if (ping <= 900) {
            maxmovement = 3.0;
        } else {
            maxmovement = 9.0;
        }

        if ((event.getTo().getY() - event.getFrom().getY()) >= maxmovement) {
            if (isSlimeblockNear(event.getPlayer())) {
                if (flywhitelist.contains(event.getPlayer())) return;
                Logger.log(player.getDisplayName() + " might be trying to bypass Fly using slimeblocks!");
                flywhitelist.add(event.getPlayer());
            } else {
                flywhitelist.remove(event.getPlayer());
                flyvl.add(event.getPlayer());
                int s = 0;
                for (int i = 0; i < flyvl.size(); i++) {
                    if (Objects.equals(event.getPlayer().getDisplayName(), event.getPlayer().getDisplayName())) ++s;
                }
                Logger.log(player.getDisplayName() + " failed Fly | Speed: " + maxmovement + " | VL " + s + " | Is authed: " + player.hasPermission("ac.execnpccmds") + " | Ping: " + getPing(player));
                if (s >= 25) {
                    for (int i = 0; i < flyvl.size(); i++) {
                        flyvl.remove(event.getPlayer());
                    }
                    Ban.ban(player);
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }
}
