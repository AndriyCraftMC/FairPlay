package FairPlay.data;

import org.bukkit.entity.Player;

public class PingManager {
    public static int getPing(Player player) {
        int ping = 0;
        try {
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ping;
    }
}
