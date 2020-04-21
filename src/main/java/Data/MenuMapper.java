/*
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
 */
package Data;

import Model.Pizza;
import Util.DBConnector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MenuMapper {

    public ArrayList<Pizza> getMenucard(){
         ArrayList<Pizza> pizzas = new ArrayList<>();

         pizzas = getAllPizzas();

        return pizzas;
    }


    private ArrayList<Pizza> getAllPizzas(){
        ArrayList<Pizza> pizzas = new ArrayList<>();
        Connection connection = DBConnector.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();

            String query = "SELECT menucard.id, " +
                    "menucard.navn, " +
                    "menucard.pris, " +
                    "GROUP_CONCAT(DISTINCT menuFilling.topping SEPARATOR \",\") " +
                    "AS \"fyld\"\n" +
                    "FROM menucard\n" +
                    "INNER JOIN menuFilling ON menuFilling.pizzaId = ID\n" +
                    "GROUP BY menucard.id;";

            ResultSet resultset = statement.executeQuery(query);

            while(resultset.next()) {
                int number = resultset.getInt("menucard.id");
                String name = resultset.getString("menucard.navn");
                double price = resultset.getDouble("menucard.pris");
                String[] fillings = resultset.getString("fyld").split(",");

                Pizza pizza = new Pizza(number,name,price,fillings);

                pizzas.add(pizza);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return pizzas;
    }
}
