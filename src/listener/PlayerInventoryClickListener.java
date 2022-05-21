package listener;

import main.Logowaniegrypa;
import object.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerInventoryClickListener implements Listener {
    private final Logowaniegrypa auth;

    public PlayerInventoryClickListener(Logowaniegrypa auth) {
        this.auth = auth;
    }

    @EventHandler
    public void onDrop(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        User u = this.auth.getUserManager().get(p.getName());
        if (!u.isLogged() || !u.isRegistered())
            e.setCancelled(true);
    }
}
