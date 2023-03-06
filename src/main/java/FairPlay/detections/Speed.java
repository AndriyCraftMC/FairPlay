package FairPlay.detections;

import FairPlay.data.Ban;
import FairPlay.log.Logger;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.Objects;

public class Speed implements Listener {
    public static ArrayList<Player> speedvl = new ArrayList<>();

    public void punish(PlayerMoveEvent event) {
        speedvl.add(event.getPlayer());
        int s = 0;
        for (int i = 0; i < speedvl.size(); i++) {
            if (Objects.equals(event.getPlayer().getDisplayName(), event.getPlayer().getDisplayName())) ++s;
        }
        Logger.log(event.getPlayer().getDisplayName() + " failed Speed | " + "VL " + s + " | Is authed: " + event.getPlayer().hasPermission("ac.execnpccmds"));
        if (s >= 35) {
            for (int i = 0; i < speedvl.size(); i++) {
                speedvl.remove(event.getPlayer());
            }
            Ban.ban(event.getPlayer());
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.SPECTATOR
                || event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }

        double z = event.getTo().getZ() - event.getFrom().getZ();
        double x = event.getTo().getX() - event.getFrom().getX();

        if (("" + x).contains("-")) {
            if (x <= -0.65) {
                punish(event);
            }
        } else if (("" + z).contains("-")) {
            if (z <= -0.65) {
                punish(event);
            }
        } else if (z >= 0.65
                || x >= 0.65) {
                punish(event);
        }
    }
}
