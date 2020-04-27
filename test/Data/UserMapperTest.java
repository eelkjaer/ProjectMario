/*
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
 */
package Data;

import Util.DBConnector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserMapperTest {
    static User actualUser;
    static User expectedUser;
    static final UserMapper userMapper = new UserMapper();

    @BeforeAll
    public static void setUp() {
        actualUser = null;
        expectedUser = new User("jacob","jacob","Jacob",false);
    }

    @Test
    @Order(1)
    public void createUser(){
        actualUser = userMapper.createUser(expectedUser);

        assertEquals(expectedUser.getName(),actualUser.getName());
    }


    @Test
    @Order(2)
    public void checkLogin() throws Exception {
        actualUser = userMapper.checkLogin(expectedUser.getUsername(), expectedUser.getPassword());

        if(actualUser==null){
            throw new Exception("Forkert login!");
        }

        assertEquals(expectedUser.getUsername(),actualUser.getUsername());
        assertEquals(expectedUser.getPassword(),actualUser.getPassword());
        assertEquals(expectedUser.getName(),actualUser.getName());
        assertEquals(expectedUser.isAdmin(),actualUser.isAdmin());
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        Connection connection = DBConnector.getInstance().getConnection();
        try {
            String query = "DELETE FROM users WHERE username=? && name=?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1,expectedUser.getUsername());
            statement.setString(2,expectedUser.getName());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            throw new SQLException();
        }
        userMapper.resetAutoIncrement();
    }
}