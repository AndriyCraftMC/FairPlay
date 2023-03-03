package FairPlay.detections;

import FairPlay.data.Items;
import FairPlay.log.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

import java.io.IOException;

public class BadNickname implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void PlayerJoinEvent (PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (e.getPlayer().getDisplayName().toLowerCase().contains("fuck")
                || e.getPlayer().getDisplayName().toLowerCase().contains("dick")
                || e.getPlayer().getDisplayName().toLowerCase().contains("gay")
                || e.getPlayer().getDisplayName().toLowerCase().contains("cum")|| e.getPlayer().getDisplayName().toLowerCase().contains("ponos")|| e.getPlayer().getDisplayName().toLowerCase().contains("retard")|| e.getPlayer().getDisplayName().toLowerCase().contains("fuck")|| e.getPlayer().getDisplayName().toLowerCase().contains("cock")|| e.getPlayer().getDisplayName().toLowerCase().contains("sperm")|| e.getPlayer().getDisplayName().toLowerCase().contains("butt")|| e.getPlayer().getDisplayName().toLowerCase().contains("ass")|| e.getPlayer().getDisplayName().toLowerCase().contains("lesbian")) {
            Logger.log(e.getPlayer().getDisplayName() + " failed BadUsername VL 100% ");
            e.getPlayer().kickPlayer("§cВаше ім'я користувача не є приємлевим для сервера. Будь ласка поміняйте його. Ваше ім'я користувача не може містити погані слова");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + e.getPlayer().getDisplayName().replace("§r", "") + " Ваше ім'я користувача не може містити погані слова");
        }
    }
}
