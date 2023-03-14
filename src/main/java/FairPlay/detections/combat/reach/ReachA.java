package FairPlay.detections.combat.reach;

import FairPlay.data.Punishment;
import FairPlay.log.Flag;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.Objects;

public class ReachA implements Listener {
    public static ArrayList<Player> reachvl = new ArrayList<>();

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player attacker = (Player) event.getDamager();
            if (event.getEntity() instanceof Player) {
                Player target = (Player) event.getEntity();
                double distance = attacker.getLocation().distance(target.getLocation());
                double attackReach = (attacker.getGameMode() == GameMode.CREATIVE) ? 5.0 : 4.0;
                if (distance > attackReach) {
                    reachvl.add(attacker);
                    int s = 0;
                    for (int i = 0; i < reachvl.size(); i++) {
                        if (Objects.equals(attacker.getDisplayName(), attacker.getDisplayName())) ++s;
                    }
                    if (s >= 5) {
                        for (int i = 0; i < reachvl.size(); i++) {
                            reachvl.remove(attacker);
                        }
                        Punishment.punish(attacker);
                    } else {
                        Flag.flag("Reach/A", attacker, "Distance: " + distance);
                    }
                }
            }
        }
    }
}
