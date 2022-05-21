package config;

import java.io.File;

import main.Logowaniegrypa;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
    public String mysql_host;

    public int mysql_port;

    public String mysql_base;

    public String mysql_user;

    public String mysql_pass;

    public String usage;

    public String not_registered;

    public String not_logged;

    public String already_logged;

    public String wrong_password;

    public String logged;

    public String not_same_password;

    public String registered;

    public String logout;

    public String already_registered;

    public String changed;

    public String login;

    public String session;

    public String register;

    public String correct_username;

    public String invalid_characters;

    public String invalid_username;

    public String max_accounts;

    public int max_reg_per_ip;

    public boolean listeners_move;

    public void load(Logowaniegrypa auth) {
        if (!auth.getDataFolder().exists())
            auth.getDataFolder().mkdirs();
        File file = new File(auth.getDataFolder(), "config.yml");
        if (!file.exists())
            auth.saveResource("config.yml", true);
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        this.mysql_host = cfg.getString("mysql.host");
        this.mysql_port = cfg.getInt("mysql.port");
        this.mysql_base = cfg.getString("mysql.base");
        this.mysql_user = cfg.getString("mysql.user");
        this.mysql_pass = cfg.getString("mysql.pass");
        this.usage = cfg.getString("messages.usage");
        this.not_registered = cfg.getString("messages.error.not_registered");
        this.not_logged = cfg.getString("messages.error.not_logged");
        this.already_logged = cfg.getString("messages.error.already_logged");
        this.wrong_password = cfg.getString("messages.error.wrong_password");
        this.logged = cfg.getString("messages.logged");
        this.not_same_password = cfg.getString("messages.error.not_same_password");
        this.registered = cfg.getString("messages.registered");
        this.logout = cfg.getString("messages.logout");
        this.already_registered = cfg.getString("messages.error.already_registered");
        this.changed = cfg.getString("messages.changed");
        this.login = cfg.getString("messages.login");
        this.session = cfg.getString("messages.session");
        this.register = cfg.getString("messages.register");
        this.correct_username = cfg.getString("messages.error.correct_username");
        this.invalid_characters = cfg.getString("messages.error.invalid_characters");
        this.invalid_username = cfg.getString("messages.error.invalid_username");
        this.max_accounts = cfg.getString("messages.error.max_accounts");
        this.max_reg_per_ip = cfg.getInt("accounts.max_reg_per_ip");
        this.listeners_move = cfg.getBoolean("listeners.move");
    }
}