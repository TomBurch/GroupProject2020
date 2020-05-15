package DBAccess;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * DBManager for creating and managing access to the Saved table (containing Saved for Later products)
 */
public class SavedManager extends DBManager {
    @Override
    protected void setup() {
        try {
            //try {
            //    PreparedStatement dropState = conn.prepareStatement("DROP TABLE SAVED");
            //    dropState.executeUpdate();
            //} catch (SQLException e) {
            //    System.out.println("SavedManager::Setup:: " + e);
            //}

            if (!checkTable("Saved")) {
                System.out.println("SavedManager::Setup:: Table saved doesn't exist");

                PreparedStatement statement = conn.prepareStatement(
                        "CREATE TABLE SAVED ("
                                + "AccountID int NOT NULL,"
                                + "ISBN varchar(17) NOT NULL,"
                                + "Quantity int NOT NULL,"
                                + "PRIMARY KEY (AccountID, ISBN))");
                statement.executeUpdate();

                System.out.println("SavedManager::Setup:: Created table saved");
            }
        } catch (SQLException e) {
            System.out.println("SavedManager::Setup:: " + e);
        }
    }
}
