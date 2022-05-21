package listener;

import java.util.Locale;

import main.Logowaniegrypa;
import object.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandListener implements Listener {
    private final Logowaniegrypa auth;

    public PlayerCommandListener(Logowaniegrypa auth) {
        this.auth = auth;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        User u = this.auth.getUserManager().get(p.getName());
        if (!u.isLogged() || !u.isRegistered()) {
            String command = e.getMessage().toLowerCase(Locale.ROOT);
            if (!command.startsWith("/changepass") && !command.startsWith("/login") && !command.startsWith("/l") && !command.startsWith("/register") && !command.startsWith("/reg"))
                e.setCancelled(true);
        }
    }
}
