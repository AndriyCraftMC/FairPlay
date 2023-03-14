package FairPlay.detections.misc.fancychat;

import FairPlay.data.Punishment;
import FairPlay.log.Flag;
import FairPlay.log.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class FancyChatA implements Listener {
    @EventHandler (priority = EventPriority.HIGHEST)
    public static void oc(AsyncPlayerChatEvent e) {
        String message = e.getMessage();
        if (containsFancyCharacters(message)) {
            e.setCancelled(true);
            Flag.flag("FancyChat/A", e.getPlayer(), "Message: " + e.getMessage());
            Punishment.punish(e.getPlayer());
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
