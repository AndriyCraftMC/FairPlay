package FairPlay.detections;

import FairPlay.data.Ban;
import FairPlay.log.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class Ad implements Listener {
    @EventHandler
    public static void onPlayerChat(PlayerChatEvent e) {
        String message = e.getMessage().toLowerCase();
        if (message.contains("best cracked server") || message.contains("join server") || message.contains("alert > ") || message.contains("join my server")) {
            e.setCancelled(true);
            Logger.log(e.getPlayer().getDisplayName() + " failed Ad (message: " + message + ") VL 100%");
            Ban.ban(e.getPlayer());
        }
    }

    @EventHandler
    public static void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        String message = e.getMessage().toLowerCase();
        if (message.contains("best cracked server") || message.contains("join server") || message.contains("alert > ") || message.contains("join my server")) {
            e.setCancelled(true);
            Logger.log(e.getPlayer().getDisplayName() + " failed Ad (message: " + message + ") VL 100%");
            Ban.ban(e.getPlayer());
        }
    }
}
