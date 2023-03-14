package FairPlay.detections.misc.dropall;

import FairPlay.data.Punishment;
import FairPlay.data.Items;
import FairPlay.log.Flag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropAllA implements Listener {
    @EventHandler (priority = EventPriority.HIGHEST)
    public void onItemDrop (PlayerDropItemEvent e)  {
        Player p = e.getPlayer();
        Items.getItems().add(p);
        int timesfound = 0;
        for (Player item : Items.getItems()) {
            if (item == p) {
                timesfound++;
                if (timesfound > 20) {
                    Flag.flag("DropAllA/A", e.getPlayer(), "Dropped 20+ items in 1 sec");
                    Punishment.punish(e.getPlayer());
                }
            }
        }
    }
}
