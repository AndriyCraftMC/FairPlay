package FairPlay.detections.combat.selfhit;

import FairPlay.data.Punishment;
import FairPlay.log.Flag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SelfhitA implements Listener {
    @EventHandler
    public void damage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player player = (Player) e.getDamager();

            if (e.getEntity() == player) {
                Flag.flag("Selfhit/A", player, "Not possible with vanilla client");
                Punishment.punish(player);
                e.setCancelled(true);
            }
        }
    }
}
