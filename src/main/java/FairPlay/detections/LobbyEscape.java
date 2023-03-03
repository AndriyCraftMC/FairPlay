package FairPlay.detections;

import FairPlay.log.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.IOException;

public class LobbyEscape implements Listener {

    @EventHandler
    public void escape(PlayerMoveEvent e) throws IOException {
        if (e.getPlayer().getWorld().equals(Bukkit.getWorld("lobby"))) {
            if (e.getPlayer().getLocation().getY() > 139) {
                Logger.log(e.getPlayer().getDisplayName() + " failed LobbyEscape (/wee). VL 0%");
                PotionEffect applyEffect = new PotionEffect(PotionEffectType.BLINDNESS, 50, 255);
                e.getPlayer().addPotionEffect(applyEffect);
                Location loc = e.getPlayer().getLocation();
                loc.setY(loc.getY() - 1);
                e.getPlayer().teleport(loc);
                e.getPlayer().sendMessage(ChatColor.RED + "Будь ласка не виходьте за лоббі!");
                e.getPlayer().chat("/initsoundself");
            } else if (e.getPlayer().getLocation().getY() > 138.9) {
                Logger.log(e.getPlayer().getDisplayName() + " failed LobbyEscape (other method). VL 0%");
                PotionEffect applyEffect = new PotionEffect(PotionEffectType.BLINDNESS, 50, 255);
                e.getPlayer().addPotionEffect(applyEffect);
                e.getPlayer().sendMessage(ChatColor.RED + "Будь ласка не виходьте за лоббі!");
                e.getPlayer().chat("/initsoundself");
            }
        }
    }
}
