package FairPlay.detections.movement.fly;

import FairPlay.data.Punishment;
import FairPlay.log.Flag;
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
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Objects;

public class FlyC implements Listener {
    public static ArrayList<Player> whitelist = new ArrayList<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && Objects.equals(event.getItem(), new ItemStack(Material.ENDER_PEARL))) {
            Flag.flag("Fly/C", player, "[DEBUG] Not punishing: Used ender pearl");
            whitelist.add(player);
        }
    }

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

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR) || player.getAllowFlight() || player.getVehicle() != null || player.isOnGround()) {
            return;
        }
        if (isSlimeblockNear(event.getPlayer())) return;
        if (player.getFallDistance() == 0.0f && player.getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.AIR)) {
            if (whitelist.contains(player)) {
                Flag.flag("Fly/C", player, "[DEBUG] Not punishing: Whitelisted");
                return;
            }

            Location to = event.getTo();
            Location from = event.getFrom();
            double distance = to.toVector().distance(from.toVector());
            if (distance > 1.0) {
                Location location = player.getLocation();
                for (int i = -10; i <= 10; ++i) {
                    for (int j = -255; j <= 255; ++j) {
                        for (int k = -10; k <= 10; ++k) {
                            Location blockLocation = location.clone().add(i, j, k);
                            if (blockLocation.getBlock().getType() == Material.SLIME_BLOCK) {
                                Flag.flag("Fly/C", player, "[DEBUG] Not punishing: Jumped on slime block");
                                whitelist.add(player);
                                return;
                            }
                        }
                    }
                }
                event.setCancelled(true);
                Flag.flag("Fly/C", player, "100% hacking");
                Punishment.punish(player);
            }
        }
    }
}
