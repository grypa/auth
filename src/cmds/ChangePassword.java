package cmds;

import object.User;
import helper.ChatHelper;
import main.Logowaniegrypa;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangePassword  implements CommandExecutor {
    private final Logowaniegrypa auth;

    public ChangePassword(Logowaniegrypa auth) {
        (this.auth = auth).getCommand("changepassword").setExecutor(this);
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player)sender;
        if (args.length == 0)
            return ChatHelper.sendMsg(p, (this.auth.getConfiguration()).usage.replace("{USAGE}", "/changepassword <stare_haslo> <nowe_haslo>"));
        User u = this.auth.getUserManager().get(p.getName());
        if (!u.isRegistered())
            return ChatHelper.sendMsg(p, (this.auth.getConfiguration()).not_registered);
        if (!u.getPassword().equals(Hashing.md5().hashBytes(args[0].getBytes(StandardCharsets.UTF_8)).toString()))
            return ChatHelper.sendMsg(p, (this.auth.getConfiguration()).wrong_password);
        u.setPassword(Hashing.md5().hashBytes(args[1].getBytes(StandardCharsets.UTF_8)).toString());
        ChatHelper.sendMsg(p, (this.auth.getConfiguration()).changed);
        return true;
    }
}
