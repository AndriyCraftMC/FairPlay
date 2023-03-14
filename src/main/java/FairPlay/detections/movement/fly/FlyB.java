package FairPlay.detections.movement.fly;

import FairPlay.data.Punishment;
import FairPlay.log.Flag;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FlyB {
    public static void runTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR) || player.getAllowFlight() || player.getVehicle() != null || player.isOnGround()) {
                        return;
                    }
                    if (player.getFallDistance() == 0.0f && player.getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.AIR)) {
                        if (player.getWorld().getBlockAt(player.getLocation()).getType() == Material.AIR && !player.isOnGround()) {
                            Flag.flag("Fly/B", player, "Was flying without moving in air for 5 sec");

                            Punishment.punish(player);
                        }
                    }
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("FairPlay"), 0L, 500L);
    }
}
