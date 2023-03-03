package FairPlay.log;

import org.bukkit.Bukkit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Logger {
    public static void log(String message) {
        try {
            Bukkit.getLogger().info(message);

            String server = Paths.get(".").toAbsolutePath().normalize().toString();
            String path = server + "//plugins//AndriyCraft//";
            String msg = message + "\n";
            Files.write(Paths.get(path + "/reports.txt"), msg.getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void eventLog(String message) {
        try {
            String server = Paths.get(".").toAbsolutePath().normalize().toString();
            String path = server + "//plugins//AndriyCraft//";
            String msg = message + "\n";
            Files.write(Paths.get(path + "/events.txt"), msg.getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
