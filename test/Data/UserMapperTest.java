package Data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import Model.User;
import Util.DBConnector;
import java.sql.*;

class UserMapperTest {
    User actualUser = null;
    String username="mario";
    String password="mario";

    @BeforeEach
    void setUp() {
        Connection connection = DBConnector.getInstance().getConnection();

        try {
            String query = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1,username);
            statement.setString(2,password);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                actualUser = new User(result.getString("username"),
                        result.getString("password"),
                        result.getString("name"),
                        result.getBoolean("admin"));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Test
    void checkLogin() {
        //Test for fail
        //User expectedUser = new User("mario","mario","Mario",false);

        //Test for success
        User expectedUser = new User("mario","mario","Mario",true);

        assertEquals(expectedUser.getUsername(),actualUser.getUsername());
        assertEquals(expectedUser.getPassword(),actualUser.getPassword());
        assertEquals(expectedUser.getName(),actualUser.getName());
        assertEquals(expectedUser.isAdmin(),actualUser.isAdmin());

    }
}