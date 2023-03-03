package FairPlay.data;

import org.bukkit.entity.Player;
import java.util.ArrayList;

public class Items {
    private static final ArrayList<Player> itemList = new ArrayList<>();

    public static ArrayList<Player> getItems() {
        return itemList;
    }
}
