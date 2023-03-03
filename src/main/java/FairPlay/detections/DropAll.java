package FairPlay.detections;

import FairPlay.data.Ban;
import FairPlay.data.Items;
import FairPlay.log.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.io.IOException;

public class DropAll implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onItemDrop (PlayerDropItemEvent e) throws IOException {
        Player p = e.getPlayer();
        Items.getItems().add(p);
        int timesfound = 0;
        for (Player item : Items.getItems()) {
            if (item == p) {
                timesfound++;
                if (timesfound > 15) {
                    Logger.log(e.getPlayer().getDisplayName() + " failed .drop all (+15 items) VL 100%");
                    Ban.ban(e.getPlayer());
                }
            }
        }
    }
}
