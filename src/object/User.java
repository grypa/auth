package object;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.Logowaniegrypa;
import org.bukkit.entity.Player;

public class User {
    private final String name;

    private boolean registered;

    private boolean logged;

    private String password;

    private final String registerIp;

    private String lastIp;

    private long lastLogin;

    public User(Player player) {
        this.name = player.getName();
        this.registered = false;
        this.logged = false;
        this.password = null;
        this.registerIp = player.getAddress().getAddress().getHostAddress();
        this.lastIp = this.registerIp;
        this.lastLogin = System.currentTimeMillis();
        insert();
    }

    public User(ResultSet rs) throws SQLException {
        this.name = rs.getString("name");
        this.registered = (rs.getInt("registered") == 1);
        this.password = rs.getString("password");
        this.registerIp = rs.getString("registerIp");
        this.lastIp = rs.getString("lastIp");
        this.lastLogin = rs.getLong("lastLogin");
    }

    private void insert() {
        Logowaniegrypa.getPlugin().getSql().executeUpdate("INSERT INTO `GrypaAuth`(`id`, `name`, `password`, `registered`, `registerIp`, `lastIp`, `lastLogin`) VALUES (NULL, '" + getName() + "','" + getPassword() + "','" + (isRegistered() ? 1 : 0) + "','" + this.registerIp + "','" + this.lastIp + "','" + this.lastLogin + "')");
    }

    public String getName() {
        return this.name;
    }

    public boolean isLogged() {
        return this.logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public void updateLastLogin(Player player) {
        this.lastIp = player.getAddress().getAddress().getHostAddress();
        this.lastLogin = System.currentTimeMillis();
        Logowaniegrypa.getPlugin().getSql().executeUpdate("UPDATE `GrypaAuth` SET `lastIp` ='" + this.lastIp + "', `lastLogin` ='" + this.lastLogin + "' WHERE `name` ='" + getName() + "'");
    }

    public void setLastLogin(long time) {
        this.lastLogin = time;
    }

    public boolean shouldAutoLogin(Player player) {
        return (this.registered && !this.logged && this.lastIp.equals(player.getAddress().getAddress().getHostAddress()) && this.lastLogin + 86400000L > System.currentTimeMillis());
    }

    public boolean isRegistered() {
        return this.registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
        Logowaniegrypa.getPlugin().getSql().executeUpdate("UPDATE `GrypaAuth` SET `registered` ='" + (registered ? 1 : 0) + "' WHERE `name` ='" + getName() + "'");
    }

    public String getPassword() {
        return this.password;
    }

    public String getLastIp() {
        return this.lastIp;
    }

    public void setPassword(String password) {
        this.password = password;
        Logowaniegrypa.getPlugin().getSql().executeUpdate("UPDATE `GrypaAuth` SET `password` ='" + password + "' WHERE `name` ='" + getName() + "'");
    }
}
