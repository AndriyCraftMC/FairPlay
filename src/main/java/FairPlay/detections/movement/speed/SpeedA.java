package FairPlay.detections.movement.speed;

import FairPlay.data.Punishment;
import FairPlay.log.Flag;
import FairPlay.log.Logger;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.Objects;

public class SpeedA implements Listener {
    public static ArrayList<Player> speedvl = new ArrayList<>();

    public void punish(PlayerMoveEvent event) {
        speedvl.add(event.getPlayer());
        int s = 0;
        for (int i = 0; i < speedvl.size(); i++) {
            if (Objects.equals(event.getPlayer().getDisplayName(), event.getPlayer().getDisplayName())) ++s;
        }
        Flag.flag("Speed/A", event.getPlayer(), "VL " + s);
        if (s >= 35) {
            for (int i = 0; i < speedvl.size(); i++) {
                speedvl.remove(event.getPlayer());
            }
            Punishment.punish(event.getPlayer());
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent event) {
        if (event.getPlayer().getWorld().getName().contains("reducer")) return;

        if (event.getPlayer().getGameMode() == GameMode.SPECTATOR
                || event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }

        double z = event.getTo().getZ() - event.getFrom().getZ();
        double x = event.getTo().getX() - event.getFrom().getX();

        double maxval = 0.36;
        
        if (event.getPlayer().isSprinting()) maxval = 0.75;
        
        if (("" + x).contains("-")) {
            if (x <= -maxval) {
                punish(event);
            }
        } else if (("" + z).contains("-")) {
            if (z <= -maxval) {
                punish(event);
            }
        } else if (z >= maxval
                || x >= maxval) {
                punish(event);
        }
    }
}
