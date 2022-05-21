package listener;

import main.Logowaniegrypa;
import object.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {
    private final Logowaniegrypa auth;

    public PlayerChatListener(Logowaniegrypa auth) {
        this.auth = auth;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        User u = this.auth.getUserManager().get(p.getName());
        if (!u.isLogged() || !u.isRegistered())
            e.setCancelled(true);
    }
}
