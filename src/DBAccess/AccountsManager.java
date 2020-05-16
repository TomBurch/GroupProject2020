package DBAccess;

import java.sql.*;

/**
 * DBManager for creating and managing access to the Accounts table
 */
public class AccountsManager extends DBManager {
    @Override
    protected void setup() {
        try {
            //try {
            //    PreparedStatement dropState = conn.prepareStatement("DROP TABLE ACCOUNTS");
            //    dropState.executeUpdate();
            //} catch (SQLException e) {
            //    System.out.println("AccountsManager::Setup:: " + e);
            //}

            if (!checkTable("ACCOUNTS")) {
               System.out.println("AccountsManager::Setup:: Table accounts doesn't exist");
               
               PreparedStatement statement = conn.prepareStatement(
                       "CREATE TABLE ACCOUNTS ("
                               + "AccountID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                               + "Username varchar(15) NOT NULL UNIQUE,"
                               + "PasswordHash varChar(65) NOT NULL,"
                               + "Email varChar(320) NOT NULL,"
                               + "Postcode varChar(9) NOT NULL,"
                               + "PRIMARY KEY (AccountID))"
               );
               statement.executeUpdate();
               
               System.out.println("AccountsManager::Setup:: Created table accounts");
            }
        } catch (SQLException e) {
            System.out.println("AccountsManager::Setup:: " + e);
        }
    }
}