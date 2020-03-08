package clients.customer;

import java.sql.*;

public abstract class DBManager
{
    //private static final String driver = "org.apache.derby.jdbc.EmbeddedDriver"; 
    private static final String dbUrl = "jdbc:derby:fizzit.db;create=true";
    protected static Connection conn;
    
    private static void shutdown() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Closing fizzit.db connection");
                try { 
                    DriverManager.getConnection("jdbc:derby:fizzit.db;shutdown=true");
                } catch (Exception e) {
                    System.out.println("DBManager::shutdown:: " + e);
                }
            }
        });
    }
    
    public DBManager() {
        try {
            conn = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            System.out.println("DBManager::constructor:: " + e);
        }
    }
    
    protected abstract void setup();
    
    protected static boolean checkTable(String tableName) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet result = meta.getTables(null, null, tableName.toUpperCase(), null);
            
        return result.next();
    }
    
    public Connection getConnection() {
        return conn;
    }
}
