package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import main.Logowaniegrypa;
import org.bukkit.plugin.Plugin;

public class SQL {
    private final Logowaniegrypa auth;

    private Connection conn;

    private final String host;

    private final int port;

    private final String pass;

    private final String base;

    private final String user;

    public final ExecutorService executor = Executors.newScheduledThreadPool(10);

    public SQL(Logowaniegrypa auth) {
        this.auth = auth;
        this.host = (auth.getConfiguration()).mysql_host;
        this.port = (auth.getConfiguration()).mysql_port;
        this.pass = (auth.getConfiguration()).mysql_pass;
        this.base = (auth.getConfiguration()).mysql_base;
        this.user = (auth.getConfiguration()).mysql_user;
        auth.getServer().getScheduler().runTaskTimer((Plugin)auth, () -> execute("SELECT CURTIME()"), 15000L, 15000L);
    }

    public boolean connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.base + "?autoReconnect=true", this.user, this.pass);
            this.auth.getLogger().info("Connected to database.");
            return true;
        } catch (ClassNotFoundException|SQLException e) {
            this.auth.getLogger().warning("Cannot connect to database!");
            e.printStackTrace();
            return false;
        }
    }

    public void execute(String query) {
        try {
            this.conn.createStatement().execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeUpdate(String update) {
        this.executor.submit(() -> {
            try {
                this.conn.createStatement().executeUpdate(update);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void executeQuery(String query, QueryCallback callback) {
        this.executor.submit(() -> {
            try (ResultSet rs = this.conn.createStatement().executeQuery(query)) {
                callback.receivedResultSet(rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void disconnect() {
        if (this.conn != null)
            try {
                this.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public boolean isConnected() {
        try {
            return (!this.conn.isClosed() || this.conn == null);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static interface QueryCallback {
        void receivedResultSet(ResultSet param1ResultSet);
    }
}
