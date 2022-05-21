package listener;

import main.Logowaniegrypa;
import object.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {
    private final Logowaniegrypa auth;

    public PlayerDropItemListener(Logowaniegrypa auth) {
        this.auth = auth;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        User u = this.auth.getUserManager().get(p.getName());
        if (!u.isLogged() || !u.isRegistered())
            e.setCancelled(true);
    }
}
