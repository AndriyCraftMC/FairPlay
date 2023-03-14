package FairPlay.detections.combat.killaura;

import FairPlay.log.Flag;
import com.destroystokyo.paper.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class KillauraC implements Listener {

    private final Map<Player, Integer> attackCounts = new HashMap<>();

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (attackCounts.containsKey(player)) {
            int count = attackCounts.get(player);
            player.sendTitle(new Title(String.valueOf(count)));
            if (count >= 25) {
                Flag.flag("Killaura/C", event.getPlayer(), "Damaged more than 25 cps in 2 sec");
                event.setCancelled(true);
            } else {
                attackCounts.put(player, count + 1);
            }
        } else {
            attackCounts.put(player, 1);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                attackCounts.remove(player);
            }
        }.runTaskLater(Bukkit.getPluginManager().getPlugin("FairPlay"), 50L);
    }
}
