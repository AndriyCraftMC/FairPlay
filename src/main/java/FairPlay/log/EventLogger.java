package FairPlay.log;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;

public class EventLogger implements Listener {

    @EventHandler
    public static void PlayerJoinEvent(PlayerJoinEvent e) {
        Logger.eventLog(e.getPlayer().getDisplayName() + " joined the server with uuid " + e.getPlayer().getUniqueId() + " with IP " + e.getPlayer().getAddress().getAddress().toString());
    }

    @EventHandler
    public static void PlayerQuitEvent(PlayerQuitEvent e) {
        Logger.eventLog(e.getPlayer().getDisplayName() + " left the server with uuid with IP " + e.getPlayer().getAddress().getAddress().toString());
    }

    @EventHandler
    public static void PlayerChatEvent(PlayerChatEvent e) {
        Logger.eventLog(e.getPlayer().getDisplayName() + " chatted: " + e.getMessage());
    }

    @EventHandler
    public static void PlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e) {
        Logger.eventLog(e.getPlayer().getDisplayName() + " used cmd: " + e.getMessage());
    }

    @EventHandler
    public static void PlayerCommandPreprocessEvent(PlayerKickEvent e) {
        Logger.eventLog(e.getPlayer().getDisplayName() + " got kicked: " + e.getReason());
    }

    @EventHandler
    public static void BlockPlaceEvent(BlockPlaceEvent e) {
        Logger.eventLog(e.getPlayer().getDisplayName() + " placed block: " + e.getBlock().toString());
    }

    @EventHandler
    public static void BlockBreakEvent(BlockBreakEvent e) {
        Logger.eventLog(e.getPlayer().getDisplayName() + " broke a block: " + e.getBlock().toString());
    }

    @EventHandler
    public static void PlayerDropItemEvent(PlayerDropItemEvent e) {
        Logger.eventLog(e.getPlayer().getDisplayName() + " dropped an item: " + e.getItemDrop().toString());
    }
}
