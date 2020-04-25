/*
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
 */
package Data;

import Model.Pizza;
import Util.DBConnector;

import java.sql.*;
import java.util.ArrayList;

public class MenuMapper {

    public ArrayList<Pizza> getMenucard(){
         ArrayList<Pizza> pizzas;

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

    public Pizza createNewPizza(Pizza pizza){
        int pizzaNumber;
        String pizzaName = pizza.getName();
        double pizzaPrice = pizza.getPrice();
        String[] pizzaFilling = pizza.getFilling();


        Connection connection = DBConnector.getInstance().getConnection();
        try {
            //Indsætter ordren i tabellen "menucard"
            String query = "INSERT INTO menucard(navn, pris) VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            statement.setString(1,pizzaName);
            statement.setDouble(2,pizzaPrice);

            statement.executeUpdate();
            ResultSet tableKeys = statement.getGeneratedKeys();
            tableKeys.next();
            pizzaNumber = tableKeys.getInt(1);
            pizza.setNumber(pizzaNumber);

            //Indsætter fyldet til pizzaen i samføjningstabellen "menuFilling"
            for(int i=0; i < pizzaFilling.length; i++){
                String filling = pizzaFilling[i];
                String query2 = "INSERT INTO menuFilling(pizzaId,topping) VALUES (?,?)";
                PreparedStatement statement2 = connection.prepareStatement(query2);

                statement2.setInt(1,pizzaNumber);
                statement2.setString(2,filling);
                statement2.execute();
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return pizza;
    }
}
