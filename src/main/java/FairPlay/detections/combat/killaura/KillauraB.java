package FairPlay.detections.combat.killaura;

import FairPlay.data.Punishment;
import FairPlay.log.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Objects;

public class KillauraB implements Listener {
    public static ArrayList<Player> killauravl = new ArrayList<>();

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player attacker = (Player) event.getDamager();
            Player attacked = (Player) event.getEntity();

            Location attackerLoc = attacker.getLocation();
            Vector attackerDir = attackerLoc.getDirection().normalize();

            Location attackedLoc = attacked.getLocation();

            Vector direction = attackedLoc.subtract(attackerLoc).toVector().normalize();

            double pitch = attackerLoc.getPitch();
            if (pitch < -45.0) {
                return;
            }

            if (!(attackerDir.dot(direction) > 0.5)) {
                Flag.flag("Killaura/B", attacker, "Attempted to hit player, while not looking at it");
                event.setCancelled(true);

                killauravl.add(attacker);
                int s = 0;
                for (int i = 0; i < killauravl.size(); i++) {
                    if (Objects.equals(attacker.getDisplayName(), attacker.getDisplayName())) ++s;
                }
                if (s >= 5) {
                    for (int i = 0; i < killauravl.size(); i++) {
                        killauravl.remove(attacker);
                    }
                    Punishment.punish(attacker);
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }
}
