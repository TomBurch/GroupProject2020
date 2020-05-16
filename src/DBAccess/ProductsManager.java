package DBAccess;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DBManager for creating and managing access to the Products table
 */
public class ProductsManager extends DBManager {
    @Override
    protected void setup() {
        try {
            try {
                PreparedStatement dropState = conn.prepareStatement("DROP TABLE PRODUCTS");
                dropState.executeUpdate();
            } catch (SQLException e) {
                System.out.println("AccountsManager::Setup:: " + e);
            }

            if (!checkTable("Products")) {
                System.out.println("ProductsManager::Setup:: Table products doesn't exist");

                PreparedStatement statement = conn.prepareStatement(
                        "CREATE TABLE PRODUCTS ("
                                + "ISBN varchar(17) NOT NULL,"
                                + "Title varchar(100) NOT NULL,"
                                + "Price decimal(5,2) NOT NULL,"
                                + "Author varchar(100),"
                                + "Publisher varchar(100),"
                                + "YearPublished date,"
                                + "Description varchar(1000),"
                                + "PRIMARY KEY (ISBN))");
                statement.executeUpdate();

                Statement s = conn.createStatement();
                s.executeUpdate("INSERT INTO Products VALUES ('978-0-64-853051-0', 'Alchemy of Awareness',                  0.50, 'Byrne, Raelene',     'Medicine For Your Spirit', '2019-06-01', 'After decades of remembering, learning and experiencing, the one thing I offer as a way forward is an ancient teaching that is more relevant today than ever before,\n\n\"Everything you need to know you already have within\"')");
                s.executeUpdate("INSERT INTO Products VALUES ('978-0-07-811269-0', 'Contemporary Management 7th Edition',   1.50, 'Garther R. Jones',   'McGraw-Hill/Irwin',        '2010-09-01', '')");
                s.executeUpdate("INSERT INTO Products VALUES ('0-133-81348-7',     'Introduction to Java programming',      1.50, 'Y. Daniel Liang',    'Pearson',                  '2013-12-27', '')");
                s.executeUpdate("INSERT INTO Products VALUES ('978-0-78-612331-5', 'The Dark Portal',                       3.50, 'Robin Jarvis',       'Chronicle Books',          '2001-08-01', 'The Dark Portal is the first book in the Deptford Mice Trilogy by Robin Jarvis')");
                s.executeUpdate("INSERT INTO Products VALUES ('978-1-60-033052-0', 'Learning SQL',                          4.50, 'Alan Beaulieu',      'Publisher 4',              '2005-08-22', 'SQL (Structured Query Language) is a standard programming language for generating, manipulating, and retrieving information from a relational database')");

                System.out.println("ProductsManager::Setup:: Created table products");
            }
        } catch (SQLException e) {
            System.out.println("ProductsManager::Setup:: " + e);
        }
    }
}