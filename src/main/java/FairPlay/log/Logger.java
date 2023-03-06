package FairPlay.log;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Logger {
    public static void log(String message) {
        try {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("FairPlay.FlagsLog")) {
                    player.sendMessage("§l§c§l(!) §r§c" + message.replace("§r","").replace("§6","").replace("§f",""));
                }
            }

            Bukkit.getLogger().info("FairPlay | " + message);

            String server = Paths.get(".").toAbsolutePath().normalize().toString();
            String path = server + "//plugins//FairPlay//";
            String msg = message + "\n";
            Files.write(Paths.get(path + "/flags.log"), msg.getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void eventLog(String message) {
        try {
            String server = Paths.get(".").toAbsolutePath().normalize().toString();
            String path = server + "//plugins//FairPlay//";
            String msg = message + "\n";
            Files.write(Paths.get(path + "/eventlog.log"), msg.getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
