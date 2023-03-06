package FairPlay;

import FairPlay.data.Items;
import FairPlay.detections.*;
import FairPlay.detections.nms.Killaura_1_12_2;
import FairPlay.log.EventLogger;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class FairPlay extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        registerEvents();
        registerScheduledTasks();
        createDataFolders();
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new DropAll(), this);
        Bukkit.getPluginManager().registerEvents(new Fly(), this);
        Bukkit.getPluginManager().registerEvents(new FancyChat(), this);
        Bukkit.getPluginManager().registerEvents(new EventLogger(), this);
        Bukkit.getPluginManager().registerEvents(new Killaura_1_12_2(), this);
        Bukkit.getPluginManager().registerEvents(new Speed(), this);
        Bukkit.getPluginManager().registerEvents(new Selfhit(), this);
        this.registerScheduledTasks();
    }

    private void registerScheduledTasks() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> Items.getItems().clear(), 5L, 5L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            Speed.speedvl.clear();
            Fly.flyvl.clear();
            Killaura_1_12_2.killauravl.clear();
        }, 900L, 900L);
    }

    private void createDataFolders () {
        try {
            File dataFolder = getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }

            File eventLogFile = new File(dataFolder, "eventlog.log");
            if (!eventLogFile.exists()) {
                eventLogFile.createNewFile();
            }

            File flagsLogFile = new File(dataFolder, "flags.log");
            if (!flagsLogFile.exists()) {
                flagsLogFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}