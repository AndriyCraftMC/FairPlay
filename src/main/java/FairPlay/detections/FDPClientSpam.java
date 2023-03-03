package FairPlay.detections;

import FairPlay.data.Ban;
import FairPlay.log.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class FDPClientSpam implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(PlayerChatEvent e) {
        String message = e.getMessage().toLowerCase();
        if (message.contains("fdpclient")) {
            e.setCancelled(true);
            String playerName = e.getPlayer().getDisplayName();
            String logMessage = playerName + " failed FDPChatSpam. (Message: " + message + "). VL 100% | Is authed: " + e.getPlayer().hasPermission("ac.execnpccmds");
            Logger.log(logMessage);
            Ban.ban(e.getPlayer());
        }
    }
}
