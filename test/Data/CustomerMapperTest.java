package Data;
import Model.Customer;
import Util.DBConnector;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CustomerMapperTest {

Customer customer;
    @BeforeEach
    public void setUp() throws Exception {
        ArrayList<Customer> customers = new ArrayList<>();
        Connection connection = DBConnector.getInstance().getConnection();
        Statement statement = connection.createStatement();

        String query = "SELECT \n" +
                            "\tcustomers.id, \n" +
                            "\tcustomers.name, \n" +
                            "\tcustomers.phone, \n" +
                            "\tcustomers.email,\n" +
                            "\t(SELECT COUNT(*) FROM orders WHERE orders.customerId=customers.id) AS \"prevOrders\"\n" +
                            "\n" +
                            "FROM customers\n" +
                            "ORDER BY customers.id;";

    ResultSet resultset = statement.executeQuery(query);

    int customerId = resultset.getInt("customers.id");
    String customerName = resultset.getString("customers.name");
    int customerPhone = resultset.getInt("customers.phone");
    String customerEmail = resultset.getString("customers.email");
    int customerOrders = resultset.getInt("prevOrders");
    Customer customer = new Customer(customerId,customerName,customerPhone,customerEmail,customerOrders);
    customers.add(customer);
    }



    @Test
    public void getId(){


     int expected = 1;
     int actual = customer.getId();

     assertEquals(expected, actual);

    }
}