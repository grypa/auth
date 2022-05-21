package cmds;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

import helper.ChatHelper;
import main.Logowaniegrypa;
import object.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Register implements CommandExecutor {
    private final Logowaniegrypa auth;

    public Register(Logowaniegrypa auth) {
        (this.auth = auth).getCommand("register").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player)sender;
        if (args.length < 2)
            return ChatHelper.sendMsg(p, (this.auth.getConfiguration()).usage.replace("{USAGE}", "/register <haslo> <haslo>"));
        User u = this.auth.getUserManager().get(p.getName());
        if (u.isRegistered())
            return ChatHelper.sendMsg(p, (this.auth.getConfiguration()).already_registered);
        if (u.isLogged())
            return ChatHelper.sendMsg(p, (this.auth.getConfiguration()).already_logged);
        if (!args[0].matches("[!-~]*"))
            return ChatHelper.sendMsg(p, (this.auth.getConfiguration()).invalid_characters);
        if (!args[0].equals(args[1]))
            return ChatHelper.sendMsg(p, (this.auth.getConfiguration()).not_same_password);
        if ((this.auth.getConfiguration()).max_reg_per_ip != 0 && this.auth.getUserManager().getByIp(p.getAddress().getAddress().getHostAddress()).size() >= (this.auth.getConfiguration()).max_reg_per_ip)
            return ChatHelper.sendMsg(p, (this.auth.getConfiguration()).max_accounts);
        u.setPassword(Hashing.md5().hashBytes(args[1].getBytes(StandardCharsets.UTF_8)).toString());
        u.setRegistered(true);
        u.setLogged(true);
        u.updateLastLogin(p);
        ChatHelper.sendMsg(p, (this.auth.getConfiguration()).registered);
        return true;
    }
}
