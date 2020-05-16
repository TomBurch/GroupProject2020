package DBAccess;

import org.jetbrains.annotations.NotNull;

import java.sql.*;

/**
 * Abstract class for creating and managing access to tables
 */
public abstract class DBManager
{
    private static final String dbUrl = "jdbc:derby:fizzit.db;create=true";
    protected static Connection conn;

    static {
        try {
            System.out.println("DBManager::constructor:: Loading EmbeddedDriver");
            conn = DriverManager.getConnection(dbUrl);
            shutdown_hook();
        } catch (Exception e) {
            System.out.println("DBManager::constructor:: " + e);
        }
    }
    
    private static void shutdown_hook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Closing fizzit.db connection");
            try {
                DriverManager.getConnection("jdbc:derby:fizzit.db;shutdown=true");
            } catch (Exception e) {
                System.out.println("DBManager::shutdown:: " + e);
            }
        }));
    }

    public DBManager() {
        setup();
    }

    /**
     * Abstract method for creating and initialising a table
     */
    protected abstract void setup();

    public Connection getConnection() {
        return conn;
    }

    /**
     * Check if the given table exists
     */
    protected static boolean checkTable(@NotNull String tableName) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet result = meta.getTables(null, null, tableName.toUpperCase(), null);
            
        return result.next();
    }
}