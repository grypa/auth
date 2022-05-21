package listener;

import main.Logowaniegrypa;
import object.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class PlayerInventoryOpenListener implements Listener {
    private final Logowaniegrypa auth;

    public PlayerInventoryOpenListener(Logowaniegrypa auth) {
        this.auth = auth;
    }

    @EventHandler
    public void onDrop(InventoryOpenEvent e) {
        Player p = (Player)e.getPlayer();
        User u = this.auth.getUserManager().get(p.getName());
        if (!u.isLogged() || !u.isRegistered())
            e.setCancelled(true);
    }
}
