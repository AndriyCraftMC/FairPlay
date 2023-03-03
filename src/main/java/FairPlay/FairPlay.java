package FairPlay;

import FairPlay.data.Items;
import FairPlay.detections.*;
import FairPlay.log.EventLogger;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class FairPlay extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new LobbyEscape(), this);
        Bukkit.getPluginManager().registerEvents(new FDPClientSpam(), this);
        Bukkit.getPluginManager().registerEvents(new DropAll(), this);
        Bukkit.getPluginManager().registerEvents(new Fly(), this);
        Bukkit.getPluginManager().registerEvents(new Ad(), this);
        Bukkit.getPluginManager().registerEvents(new BadNickname(), this);
        Bukkit.getPluginManager().registerEvents(new FancyChat(), this);
        Bukkit.getPluginManager().registerEvents(new EventLogger(), this);
        Bukkit.getPluginManager().registerEvents(new Killaura(), this);
        Bukkit.getPluginManager().registerEvents(new Speed(), this);
        Bukkit.getPluginManager().registerEvents(new Selfhit(), this);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                Items.getItems().clear();
            }
        }, 5L, 5L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                Killaura.vl1.clear();
                Killaura.vl4.clear();
                Killaura.vl5.clear();
            }
        }, 60L, 60L);

    }
}
