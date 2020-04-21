/*
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
 */
package Data;

import Model.Ordre;
import Model.Pizza;
import Util.DBConnector;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrdreMapper {
    /* TODO: Hent alle ordre fra MySQL
    public ArrayList<Ordre> getAllOrders(){
        ArrayList<Ordre> orders = new ArrayList<>();

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


                Ordre ordre = null;

                orders.add(ordre);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return orders;
    }
    */

    public void createNewOrder(Ordre ordre){
        int ordreId = ordre.getOrderNumber();
        boolean inStore = ordre.isInStore();
        int customerId = ordre.getCustomer().getId();
        LocalDateTime time = ordre.getTimestamp();
        String status = "working";
        String comment = ordre.getComment();
        double totalPrice = ordre.getPrice();


        Connection connection = DBConnector.getInstance().getConnection();
        try {
            //Indsætter ordren i tabellen "Ordre"
            String query = "INSERT INTO orders(id,pickup,customerId,readyTime,status,comment,totalPrice) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1,ordreId);
            statement.setBoolean(2,inStore);
            statement.setInt(3,customerId);
            statement.setTime(4,Time.valueOf(time.toLocalTime()));
            statement.setString(5,status);
            statement.setString(6,comment);
            statement.setDouble(7,totalPrice);
            statement.execute();

            //Indsætter pizzaerne til ordren i samføjningstabellen "ordersPizza"
            for(Pizza p: ordre.getPizzas()){
                String query2 = "INSERT INTO ordersPizza(orderId,pizzaId) VALUES (?,?)";
                PreparedStatement statement2 = connection.prepareStatement(query2);

                statement.setInt(1,ordreId);
                statement.setInt(2,p.getNumber());
                statement.execute();
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void setAsDone(Ordre ordre){
        Connection connection = DBConnector.getInstance().getConnection();
        int id = ordre.getOrderNumber();
        try {
            String query = "UPDATE orders SET status='done' WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1,id);

            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        ordre.setDone(true);
    }

}
