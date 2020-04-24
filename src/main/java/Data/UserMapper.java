/*
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
 */
package Data;

import Model.User;
import Util.DBConnector;
import java.sql.*;

public class UserMapper {

    public User checkLogin(String username, String password){
        User tmpUser;
        Connection connection = DBConnector.getInstance().getConnection();
        try {
            String query = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1,username);
            statement.setString(2,password);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                //korrekt login
                tmpUser = new User(username,password,result.getString("name"),result.getInt("level"));
                return tmpUser;
            } else {
                //forkert login
                return null;
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }
}
