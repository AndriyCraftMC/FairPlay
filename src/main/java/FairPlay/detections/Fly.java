// Decompiled with: CFR 0.152
// Class Version: 8
package FairPlay.detections;
import FairPlay.data.Ban;
import FairPlay.log.Logger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Fly implements Listener {
    @EventHandler(priority=EventPriority.HIGHEST)
    public void move(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (!player.getAllowFlight()) {
            if((e.getTo().getY() - e.getFrom().getY()) >= 0.7) {
                for (int x = -10; x <= 10; ++x) {
                    for (int y = -255; y <= 255; ++y) {
                        for (int z = -10; z <= 10; ++z) {
                            Location blockLoc = e.getPlayer().getLocation().clone().add(x, y, z);
                            if (blockLoc.getBlock().getType() != Material.SLIME_BLOCK) continue;
                            Logger.log(e.getPlayer().getDisplayName() + " failed Fly (had like ~5 blocks per sec speed; jumped on slime blocks; there is a block in 15 radius) VL 30% | Is authed: " + e.getPlayer().hasPermission("ac.execnpccmds"));
                            return;
                        }
                    }
                }
                Logger.log(e.getPlayer().getDisplayName() + " failed Fly (had 0.7 blocks per sec speed (y)) VL 100% | Is authed: " + e.getPlayer().hasPermission("ac.execnpccmds"));
                Ban.ban(e.getPlayer());
            }
        }
    }

}
