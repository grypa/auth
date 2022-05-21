package cmds;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

import object.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import main.Logowaniegrypa;
import helper.ChatHelper;

public class Login implements CommandExecutor {
    private final Logowaniegrypa auth;

    public Login(Logowaniegrypa auth) {
        (this.auth = auth).getCommand("login").setExecutor(this);
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player)sender;
        if (args.length == 0)
            return ChatHelper.sendMsg(p, (this.auth.getConfiguration()).usage.replace("{USAGE}", "/login <haslo>"));
        User u = this.auth.getUserManager().get(p.getName());
        if (!u.isRegistered())
            return ChatHelper.sendMsg(p, (this.auth.getConfiguration()).not_registered);
        if (u.isLogged())
            return ChatHelper.sendMsg(p, (this.auth.getConfiguration()).already_logged);
        if (!u.getPassword().equals(Hashing.md5().hashBytes(args[0].getBytes(StandardCharsets.UTF_8)).toString()))
            return ChatHelper.sendMsg(p, (this.auth.getConfiguration()).wrong_password);
        u.setLogged(true);
        u.updateLastLogin(p);
        ChatHelper.sendMsg(p, (this.auth.getConfiguration()).logged);
        return true;
    }
}
