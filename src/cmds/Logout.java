package cmds;

import helper.ChatHelper;
import main.Logowaniegrypa;
import object.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Logout implements CommandExecutor {
    private final Logowaniegrypa auth;

    public Logout(Logowaniegrypa auth) {
        (this.auth = auth).getCommand("logout").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player)sender;
        User u = this.auth.getUserManager().get(p.getName());
        if (!u.isRegistered())
            return ChatHelper.sendMsg(p, (this.auth.getConfiguration()).not_registered);
        if (!u.isLogged())
            return ChatHelper.sendMsg(p, (this.auth.getConfiguration()).not_logged);
        u.setLogged(false);
        u.setLastLogin(0L);
        ChatHelper.sendMsg(p, (this.auth.getConfiguration()).logout);
        return true;
    }
}
