package FairPlay.detections;

import FairPlay.data.Ban;
import FairPlay.log.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class FancyChat implements Listener {
    @EventHandler (priority = EventPriority.HIGHEST)
    public static void oc(PlayerChatEvent e) {
        String message = e.getMessage();
        if (containsFancyCharacters(message)) {
            e.setCancelled(true);
            String playerName = e.getPlayer().getDisplayName();
            String logMessage = playerName + " failed FancyChat. Message: " + message + " . VL 95% | Is authed: " + e.getPlayer().hasPermission("ac.execnpccmds");
            Logger.log(logMessage);
            e.getPlayer().kickPlayer("§cВтрачено підключення до сервера");
            Ban.ban(e.getPlayer());
        }
    }

    private static boolean containsFancyCharacters(String message) {
        String fancyChars = "ｑｗｅｒｔｙｕｉｏｐａｓｄｆｇｈｊｋｌｚｘｃｖｂｎｍ１２３４５６７８９０！＠＃＄％＾＆＊（）＿＋－＝｛｝｜＼：；＂＇＜＞，．？／";
        for (char c : message.toCharArray()) {
            if (fancyChars.indexOf(c) >= 0) {
                return true;
            }
        }
        return false;
    }
}
