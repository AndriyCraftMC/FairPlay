package FairPlay.detections.movement.fly;

import FairPlay.data.Punishment;
import FairPlay.log.Flag;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.Objects;

public class FlyD implements Listener {
    public static ArrayList<Player> vls = new ArrayList<>();

    public static ArrayList<Player> gvls = new ArrayList<>();

    public static boolean isSlimeblockNear(Player player) {
        Location playerLocation = player.getLocation();
        World world = player.getWorld();

        int radius = 5;

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


    @EventHandler
    public void flyd_move(PlayerMoveEvent event) {
            int fromY = event.getFrom().getBlockY();
            int toY = event.getTo().getBlockY();

            if (event.getPlayer().getGameMode() == GameMode.CREATIVE || event.getPlayer().getGameMode() == GameMode.SPECTATOR || isSlimeblockNear(event.getPlayer())) return;

            // Check the difference in yaw and pitch
            double fromYaw = event.getFrom().getYaw();
            double toYaw = event.getTo().getYaw();
            double fromPitch = event.getFrom().getPitch();
            double toPitch = event.getTo().getPitch();
            double yawThreshold = 1.0; // Adjust this value to your liking
            double pitchThreshold = 1.0; // Adjust this value to your liking
            if (Math.abs(fromYaw - toYaw) > yawThreshold || Math.abs(fromPitch - toPitch) > pitchThreshold) {
                return;
            }

            if (fromY == toY) {
                if (
                        event.getPlayer().getWorld().getBlockAt(event.getPlayer().getLocation().getBlockX(), event.getPlayer().getLocation().getBlockY() - 1, event.getPlayer().getLocation().getBlockZ()).getType()
                                == Material.AIR
                ) {
                    vls.add(event.getPlayer());
                    gvls.add(event.getPlayer());
                    int s = 0;
                    for (int i = 0; i < vls.size(); i++) {
                        if (Objects.equals(vls.get(i).getDisplayName(), event.getPlayer().getDisplayName())) ++s;
                    }

                    if (s >= 10) {
                        Flag.flag("Fly/D", event.getPlayer(), "Player is running in air");
                        event.setCancelled(true);
                    }

                    int s1 = 0;
                    for (int i = 0; i < gvls.size(); i++) {
                        if (Objects.equals(gvls.get(i).getDisplayName(), event.getPlayer().getDisplayName())) ++s1;
                    }
                    if (s1 > 15) {
                        Punishment.punish(event.getPlayer());
                        event.setCancelled(true);
                    }
                }
            }
    }
}