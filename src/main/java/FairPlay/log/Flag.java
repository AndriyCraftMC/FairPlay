package FairPlay.log;

import org.bukkit.entity.Player;

public class Flag {
    public static void flag(String check, Player p, String etc) {
        Logger.log(p.getDisplayName() + " failed " + check + " | " + etc + " | Is authed: " + p.hasPermission("ac.execnpccmds"));
    }
}
