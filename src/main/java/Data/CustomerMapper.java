/*
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
 */
package Data;

import Model.Customer;
import Util.DBConnector;

import java.sql.*;
import java.util.ArrayList;

public class CustomerMapper {
    public ArrayList<Customer> getAllCustomers(){
        ArrayList<Customer> customers = new ArrayList<>();

        Connection connection = DBConnector.getInstance().getConnection();
        try {
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

            while(resultset.next()) {
                int customerId = resultset.getInt("customers.id");
                String customerName = resultset.getString("customers.name");
                int customerPhone = resultset.getInt("customers.phone");
                String customerEmail = resultset.getString("customers.email");
                int customerOrders = resultset.getInt("prevOrders");


                Customer customer = new Customer(customerId,customerName,customerPhone,customerEmail,customerOrders);

                customers.add(customer);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return customers;
    }

    public void createNewCustomer(Customer customer){
        int id = customer.getId();
        String name = customer.getName();
        int phone = customer.getPhoneNo();
        String mail = customer.getMail();

        Connection connection = DBConnector.getInstance().getConnection();
        try {
            String query = "INSERT INTO customers(id, name, phone, email) VALUES (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1,id);
            statement.setString(2,name);
            statement.setInt(3,phone);
            statement.setString(4,mail);

            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

}
