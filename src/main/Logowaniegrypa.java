package main;

import cmds.Logout;
import cmds.Register;
import config.Config;
import cmds.ChangePassword;
import cmds.Login;
import listener.*;
import manager.UserManager;
import mysql.SQL;
import task.AuthTask;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.concurrent.TimeUnit;

public class Logowaniegrypa extends JavaPlugin {
    private static Logowaniegrypa plugin;

    private Config configuration;

    private SQL sql;

    private UserManager userManager;

    public static Logowaniegrypa getPlugin() {
        return plugin;
    }
    public Config getConfiguration() {
        return this.configuration;
    }
    public SQL getSql() {
        return this.sql;
    }

    public UserManager getUserManager() {
        return userManager;
    }
    public Logowaniegrypa() {
        plugin = this;
    }

    public void onEnable() {
        (this.configuration = new Config()).load(this);
        if ((this.sql = new SQL(this)).connect()) {
            this.sql.executeUpdate("CREATE TABLE IF NOT EXISTS `GrypaAuth` (`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, `name` varchar(32) NOT NULL, `password` text NOT NULL, `registered` int(1), `registerIp` text NOT NULL, `lastIp` text NOT NULL, `lastLogin` BIGINT(22) NOT NULL);");
        } else {
            getServer().shutdown();
        }
        (this.userManager = new UserManager()).load(this);
        new ChangePassword(this);
        new Login(this);
        new Register(this);
        new Logout(this);
        (new AuthTask(this)).runTaskTimerAsynchronously((Plugin)this, 80L, 80L);
        getServer().getPluginManager().registerEvents((Listener)new PlayerJoinListener(this), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new PlayerChatListener(this), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new PlayerCommandListener(this), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new PlayerQuitListener(this), (Plugin)this);
        if (!this.configuration.listeners_move)
            getServer().getPluginManager().registerEvents((Listener)new PlayerMoveListener(this), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new PlayerDropItemListener(this), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new PlayerInteractListener(this), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new PlayerInventoryOpenListener(this), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new PlayerInventoryClickListener(this), (Plugin)this);
    }

    public void onDisable() {
        getServer().getScheduler().cancelTasks((Plugin)this);
        if (this.sql.isConnected()) {
            this.sql.executor.shutdown();
            try {
                this.sql.executor.awaitTermination(10L, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.sql.disconnect();
        }
    }

    private void connectToDatabase() {}
}
