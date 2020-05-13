package DBAccess;

import java.sql.*;

public class AccountsManager extends DBManager
{
    public AccountsManager() {
       setup();
    }
    
    @Override
    protected void setup() {
        try {
            PreparedStatement dropState = conn.prepareStatement("DROP TABLE ACCOUNTS");
            dropState.executeUpdate();
            if (!checkTable("ACCOUNTS")) {
               System.out.println("AccountsManager::Setup:: Table accounts doesn't exist");
               
               PreparedStatement statement = conn.prepareStatement(
                       "CREATE TABLE ACCOUNTS ("
                       + "AccountID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                       + "Username varchar(15) NOT NULL,"
                       + "PasswordHash varChar(65) NOT NULL,"
                       + "Email varChar(320) NOT NULL,"
                       + "Postcode varChar(9) NOT NULL,"
                       + "PRIMARY KEY (AccountID))");
               statement.executeUpdate();
               
               System.out.println("AccountsManager::Setup:: Created table accounts");
            }
        } catch (SQLException e) {
            System.out.println("AccountsManager::Setup:: " + e);
        }
    }
}
