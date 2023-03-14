package FairPlay.data;

import FairPlay.log.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Punishment {
    public static void punish(Player p) {
            Logger.log("Player " + p.getDisplayName().replace("§r", "") + " was detected for hacking! (IP: " + p.getAddress().getAddress().getHostAddress() + ")");
            Bukkit.broadcastMessage("§l§c§l(!) §r§c" + p.getDisplayName().replace("§r", "") + " був забанений FairPlay за читерство");
            p.kickPlayer("§cВтрачено підключення до сервера");
            if (p.hasPermission("ac.execnpccmds")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tempban " + p.getAddress().getAddress().getHostAddress().replace("/", "") + " -s 15 день Вас було забанено за читерство нашим античитом");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tempban " + p.getDisplayName().replace("§r", "").replace("§4", "").replace("§6", "").replace("§f", "") + " -s 15 день Вас було забанено за читерство нашим античитом");
            }
        }
}