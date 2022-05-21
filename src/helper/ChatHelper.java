package helper;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class ChatHelper {
    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg).replace(">>", ">").replace("<<", "<");
    }

    public static boolean sendMsg(Player p, String msg) {
        p.sendMessage(color(msg));
        return true;
    }
}
