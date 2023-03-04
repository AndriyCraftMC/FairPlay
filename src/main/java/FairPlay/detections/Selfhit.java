package FairPlay.detections;

import FairPlay.data.Ban;
import FairPlay.log.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Selfhit implements Listener {
    @EventHandler
    public void damage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player player = (Player) e.getDamager();

            if (e.getEntity() == player) {
                e.setCancelled(true);
                Logger.log(player.getDisplayName() + " failed Selfhit VL 100% | Is authed: " + player.hasPermission("ac.execnpccmds"));
                Ban.ban(player);
            }
        }
    }
}
