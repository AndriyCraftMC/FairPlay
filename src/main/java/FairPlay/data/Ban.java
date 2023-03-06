package FairPlay.data;

import FairPlay.log.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Ban {
    public static void ban(Player p) {
        Logger.log("[!] Punished " + p.getDisplayName().replace("§r", ""));
        Bukkit.broadcastMessage("§l§c§l(!) §r§c" + p.getDisplayName().replace("§r", "")  + " був забанений FairPlay за читерство");
        p.kickPlayer("§cВтрачено підключення до сервера");
        if (p.hasPermission("ac.execnpccmds")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tempban " + p.getAddress().getAddress().getHostAddress().replace("/", "") + " -s 15 день Вас було забанено за читерство нашим античитом");
        }
    }
}
