package task;

import helper.ChatHelper;
import main.Logowaniegrypa;
import object.User;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class AuthTask extends BukkitRunnable {
    private final Logowaniegrypa auth;

    public AuthTask(Logowaniegrypa auth) {
        this.auth = auth;
    }

    public void run() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            User u = this.auth.getUserManager().get(p.getName());
            if (!u.isRegistered())
                ChatHelper.sendMsg(p, (this.auth.getConfiguration()).register);
            if (!u.isLogged() && u.isRegistered())
                ChatHelper.sendMsg(p, (this.auth.getConfiguration()).login);
        });
    }
}
