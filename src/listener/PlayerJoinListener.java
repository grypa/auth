package listener;

import java.util.regex.Pattern;

import helper.ChatHelper;
import main.Logowaniegrypa;
import object.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerJoinListener implements Listener {
    private final Logowaniegrypa auth;

    private final Pattern pattern;

    public PlayerJoinListener(Logowaniegrypa auth) {
        this.auth = auth;
        this.pattern = Pattern.compile("^[0-9a-zA-Z-_]+$");
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        if (!this.pattern.matcher(p.getName()).find()) {
            e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, ChatHelper.color((this.auth.getConfiguration()).invalid_username));
            return;
        }
        User u = this.auth.getUserManager().getIgnoreCase(p.getName());
        if (u != null && !u.getName().equals(p.getName()))
            e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, ChatHelper.color((this.auth.getConfiguration()).correct_username.replace("{NAME}", u.getName())));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        User u = this.auth.getUserManager().get(p.getName());
        if (u == null)
            u = this.auth.getUserManager().create(p);
        if (u.shouldAutoLogin(p)) {
            u.setLogged(true);
            u.updateLastLogin(p);
            ChatHelper.sendMsg(p, (this.auth.getConfiguration()).session);
            return;
        }
        if (!u.isRegistered())
            ChatHelper.sendMsg(p, (this.auth.getConfiguration()).register);
        if (!u.isLogged() && u.isRegistered())
            ChatHelper.sendMsg(p, (this.auth.getConfiguration()).login);
    }
}
