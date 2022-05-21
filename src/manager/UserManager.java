package manager;

import java.sql.ResultSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import main.Logowaniegrypa;
import object.User;
import org.bukkit.entity.Player;

public class UserManager {
    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    public User get(String name) {
        return this.users.get(name);
    }

    public User getIgnoreCase(String name) {
        return this.users.values().stream().filter(user -> user.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    public Set<User> getByIp(String ip) {
        return (Set<User>)this.users.values().stream().filter(user -> user.getLastIp().equals(ip)).collect(Collectors.toSet());
    }

    public User create(Player player) {
        User u = new User(player);
        this.users.put(player.getName(), u);
        return u;
    }

    public void load(Logowaniegrypa auth) {
        auth.getSql().executeQuery("SELECT * FROM `GrypaAuth`", rs -> {
            try {
                while (rs.next()) {
                    User u = new User(rs);
                    this.users.put(u.getName(), u);
                }
                auth.getLogger().info("Loaded " + this.users.size() + " players.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
