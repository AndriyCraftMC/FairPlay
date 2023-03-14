package FairPlay;

import FairPlay.data.Items;
import FairPlay.detections.combat.killaura.KillauraB;
import FairPlay.detections.combat.killaura.KillauraA;
import FairPlay.detections.combat.killaura.KillauraC;
import FairPlay.detections.combat.reach.ReachA;
import FairPlay.detections.combat.selfhit.SelfhitA;
import FairPlay.detections.misc.dropall.DropAllA;
import FairPlay.detections.misc.fancychat.FancyChatA;
import FairPlay.detections.movement.fly.*;
import FairPlay.detections.movement.speed.SpeedA;
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
        Bukkit.getPluginManager().registerEvents(new DropAllA(), this);
        Bukkit.getPluginManager().registerEvents(new FlyA(), this);
        FlyB.runTask();
        Bukkit.getPluginManager().registerEvents(new FlyC(), this);
        Bukkit.getPluginManager().registerEvents(new FlyD(), this);
        Bukkit.getPluginManager().registerEvents(new FancyChatA(), this);
        Bukkit.getPluginManager().registerEvents(new EventLogger(), this);
        Bukkit.getPluginManager().registerEvents(new KillauraA(), this);
        Bukkit.getPluginManager().registerEvents(new KillauraB(), this);
        Bukkit.getPluginManager().registerEvents(new KillauraC(), this);
        Bukkit.getPluginManager().registerEvents(new SpeedA(), this);
        Bukkit.getPluginManager().registerEvents(new SelfhitA(), this);
        Bukkit.getPluginManager().registerEvents(new ReachA(), this);
    }

    private void registerScheduledTasks() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> Items.getItems().clear(), 5L, 5L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            SpeedA.speedvl.clear();
            FlyA.flyvl.clear();
            KillauraA.killauravl.clear();
            KillauraB.killauravl.clear();
            ReachA.reachvl.clear();
        }, 900L, 900L);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            FlyD.vls.clear();
        }, 20L, 20L);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            FlyD.gvls.clear();
        }, 50L, 50L);

        FlyB.runTask();
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