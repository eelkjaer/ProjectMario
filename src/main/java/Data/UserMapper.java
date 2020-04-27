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
                tmpUser = new User(username,password,result.getString("name"),result.getBoolean("admin"));
                return tmpUser;
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    public User createUser(User user){
        Connection connection = DBConnector.getInstance().getConnection();
        try {
            //Indsætter ordren i tabellen "menucard"
            String query = "INSERT INTO users(username, name, password, admin) VALUES (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1,user.getUsername());
            statement.setString(2,user.getName());
            statement.setString(3,user.getPassword());
            statement.setBoolean(4,user.isAdmin());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return user;
    }

    protected void resetAutoIncrement(){
        Connection connection = DBConnector.getInstance().getConnection();
        try {
            //Indsætter ordren i tabellen "menucard"
            String query = "ALTER TABLE `users` AUTO_INCREMENT = 1";
            Statement statement = connection.prepareStatement(query);

            statement.execute(query);

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
