package listener;

import main.Logowaniegrypa;
import object.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    private final Logowaniegrypa auth;

    public PlayerMoveListener(Logowaniegrypa auth) {
        this.auth = auth;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
            Player p = e.getPlayer();
            User u = this.auth.getUserManager().get(p.getName());
            if (!u.isLogged() || !u.isRegistered())
                p.teleport(e.getFrom());
        }
    }
}
