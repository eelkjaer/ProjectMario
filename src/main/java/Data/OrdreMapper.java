/*
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
 */
package Data;

import Model.Customer;
import Model.Ordre;
import Model.Pizza;
import Util.DBConnector;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrdreMapper {

    public ArrayList<Ordre> getAllOrders(ArrayList<Customer> customers, ArrayList<Pizza> pizzas){
        ArrayList<Ordre> orders = new ArrayList<>();

        Connection connection = DBConnector.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();

            String query = "SELECT orders.id, " +
                    "orders.pickup, " +
                    "orders.customerId, " +
                    "orders.readyTime, " +
                    "orders.status, " +
                    "orders.comment, " +
                    "orders.createdBy, " +
                    "orders.totalPrice, " +
                    "GROUP_CONCAT(DISTINCT ordersPizza.pizzaId SEPARATOR \",\") " +
                    "AS \"pizzaer\"\n" +
                    "FROM orders\n" +
                    "INNER JOIN ordersPizza ON ordersPizza.orderId = orders.id\n" +
                    "GROUP BY orders.id;";

            ResultSet resultset = statement.executeQuery(query);

            while(resultset.next()) {
                int orderId = resultset.getInt("orders.id");
                boolean inStore = resultset.getBoolean("orders.pickup");
                int customerId = resultset.getInt("orders.customerId");
                Customer customer = new Customer().getCustomerById(customerId, customers);
                LocalDateTime readyTime = LocalDateTime.of(LocalDate.now(),resultset.getTime("orders.readyTime").toLocalTime());
                boolean isDone;
                String status = resultset.getString("orders.status");
                isDone = status.equals("done");
                double totalPrice = resultset.getDouble("orders.totalPrice");
                String comment = resultset.getString("orders.comment");
                String createdBy = resultset.getString("orders.createdBy");

                String[] pizzaListe = resultset.getString("pizzaer").split(",");
                ArrayList<Pizza> tmpPizzas = new ArrayList<>();
                Pizza foundPizza;

                for (String s : pizzaListe) {

                    for (Pizza p : pizzas) {
                        if (p.getNumber() == Integer.parseInt(s)) {
                            foundPizza = p;
                            tmpPizzas.add(foundPizza);
                            break;
                        }
                    }
                }

                Ordre ordre = new Ordre(orderId,inStore, isDone,readyTime,customer,tmpPizzas,totalPrice,comment,createdBy);

                orders.add(ordre);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return orders;
    }


    public Ordre createNewOrder(Ordre ordre){
        int ordreId;
        boolean inStore = ordre.isInStore();
        int customerId = ordre.getCustomer().getId();
        LocalDateTime time = ordre.getTimestamp();
        String status = "working";
        String comment = ordre.getComment();
        double totalPrice = ordre.getPrice();
        String createdBy = ordre.getCreatedBy();


        Connection connection = DBConnector.getInstance().getConnection();
        try {
            //Indsætter ordren i tabellen "Ordre"
            String query = "INSERT INTO orders(pickup,customerId,readyTime,status,comment,totalPrice, createdBy) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            statement.setBoolean(1,inStore);
            statement.setInt(2,customerId);
            statement.setTime(3,Time.valueOf(time.toLocalTime()));
            statement.setString(4,status);
            statement.setString(5,comment);
            statement.setDouble(6,totalPrice);
            statement.setString(7,createdBy);
            statement.executeUpdate();
            ResultSet tableKeys = statement.getGeneratedKeys();
            tableKeys.next();
            ordreId = tableKeys.getInt(1);
            ordre.setOrderNumber(ordreId);

            //Indsætter pizzaerne til ordren i samføjningstabellen "ordersPizza"
            for(Pizza p: ordre.getPizzas()){
                String query2 = "INSERT INTO ordersPizza(orderId,pizzaId) VALUES (?,?)";
                PreparedStatement statement2 = connection.prepareStatement(query2);

                statement2.setInt(1,ordreId);
                statement2.setInt(2,p.getNumber());
                statement2.execute();
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return ordre;
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


    /**
     * @param sort "salg" = pizza med højest omsætning
     *             "antal" = mest solgte pizza på antal
     */
    public void getPizzaStats(String sort){
        Connection connection = DBConnector.getInstance().getConnection();

        if(!sort.equals("salg") || !sort.equals("antal")){
            sort = "salg";
        }

        try {
            Statement statement = connection.createStatement();

            String query = "SELECT menucard.navn, ordersPizza.pizzaId as nummer, COUNT(ordersPizza.pizzaId) AS antal, menucard.pris * COUNT(ordersPizza.pizzaId) as salg\n" +
                    "FROM ordersPizza\n" +
                    "INNER JOIN menucard ON ordersPizza.pizzaId = menucard.id\n" +
                    "GROUP BY pizzaId\n" +
                    "ORDER BY " + sort + " DESC";

            ResultSet resultset = statement.executeQuery(query);

            double total = 0.0;

            while(resultset.next()) {
                int nummer = resultset.getInt("nummer");
                int antal = resultset.getInt("antal");
                double pris = resultset.getDouble("salg");
                String navn = resultset.getString("navn");
                total += pris;

                System.out.print("(nr." + nummer + ") " + navn + " - " + antal + " stk solgt = " + pris + "kr\n");
            }
            System.out.println(String.format("%nTotal omsætning: %.2f kr%n",total));
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
