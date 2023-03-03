package FairPlay.detections;

import FairPlay.data.Ban;
import FairPlay.log.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Speed implements Listener {
    @EventHandler
    public void f(PlayerMoveEvent e) {
        if ((e.getTo().getX() - e.getFrom().getX()) >= 0.7) {
            Logger.log(e.getPlayer().getDisplayName() + " failed Speed (X) VL 100% | Is authed: " + e.getPlayer().hasPermission("ac.execnpccmds"));
            Ban.ban(e.getPlayer());
            e.setCancelled(true);
        }

        if ((e.getTo().getZ() - e.getFrom().getZ()) >= 0.7) {
            Logger.log(e.getPlayer().getDisplayName() + " failed Speed (Z) VL 100% | Is authed: " + e.getPlayer().hasPermission("ac.execnpccmds"));
            Ban.ban(e.getPlayer());
            e.setCancelled(true);
        }
    }
}
