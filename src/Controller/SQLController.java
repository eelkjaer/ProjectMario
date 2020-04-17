/*
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
 */
package Controller;
import Model.Customer;
import Model.Ordre;
import Model.Pizza;

import java.sql.*;
import java.util.ArrayList;

public class SQLController {

    private final String myDriver = "org.gjt.mm.mysql.Driver";
    private String myUrl;
    private String dbUser;
    private String dbPsw;
    private Connection conn;

    public SQLController(String myUrl, String dbUser, String dbPsw, boolean SSL) throws ClassNotFoundException {
        this.myUrl = myUrl;
        this.dbUser = dbUser;
        this.dbPsw = dbPsw;
        if(!SSL){
            this.myUrl += "?useSSL=false";
        }

        try {
            Class.forName(this.myDriver);
            this.conn = DriverManager.getConnection(this.myUrl, this.dbUser, this.dbPsw);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public ArrayList<Customer> importCustomers(){
        ArrayList<Customer> tmpCustomers = new ArrayList<>();
        try
        {
            // create our mysql database connection

            Class.forName(this.myDriver);
            Connection conn = DriverManager.getConnection(this.myUrl, this.dbUser, this.dbPsw);

            // our SQL SELECT query.
            // if you only need a few columns, specify them by name instead of using "*"
            String query = "SELECT \n" +
                    "\tcustomers.id, \n" +
                    "\tcustomers.name, \n" +
                    "\tcustomers.phone, \n" +
                    "\tcustomers.email,\n" +
                    "\t(SELECT COUNT(*) FROM orders WHERE orders.customerId=customers.id) AS \"prevOrders\"\n" +
                    "\n" +
                    "FROM customers\n" +
                    "ORDER BY customers.id;";

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next())
            {
                int custId = rs.getInt("customers.id");
                String name = rs.getString("customers.name");
                int phone = rs.getInt("customers.phone");
                String mail = rs.getString("customers.email");
                int tidlOrdre = rs.getInt("prevOrders");

                Customer tmpCustomer = new Customer(name, phone, mail, tidlOrdre);
                tmpCustomers.add(tmpCustomer);

            }
            st.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return tmpCustomers;
    }

    public ArrayList<Pizza> importPizzas(){
        ArrayList<Pizza> tmpPizzas = new ArrayList<>();
        try
        {
            // our SQL SELECT query.
            // if you only need a few columns, specify them by name instead of using "*"
            String query = "SELECT menucard.id, " +
                    "menucard.navn, " +
                    "menucard.pris, " +
                    "GROUP_CONCAT(DISTINCT menuFilling.topping SEPARATOR \",\") " +
                    "AS \"fyld\"\n" +
                    "FROM menucard\n" +
                    "INNER JOIN menuFilling ON menuFilling.pizzaId = ID\n" +
                    "GROUP BY menucard.id;";

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next())
            {
                int id = rs.getInt("menucard.id");
                String name = rs.getString("menucard.navn");
                double price = rs.getDouble("menucard.pris");
                String filling = rs.getString("fyld");
                String[] fillings = filling.split(",");

                Pizza tmpPizza = new Pizza(id,name,price,fillings);
                tmpPizzas.add(tmpPizza);

            }
            st.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return tmpPizzas;
    }

    public ArrayList<Ordre> importOrders(){
        ArrayList<Ordre> tmpOrders = new ArrayList<>();
        try
        {
            // our SQL SELECT query.
            // if you only need a few columns, specify them by name instead of using "*"
            String query = "SELECT menucard.id, " +
                    "menucard.navn, " +
                    "menucard.pris, " +
                    "GROUP_CONCAT(DISTINCT menuFilling.topping SEPARATOR \",\") " +
                    "AS \"fyld\"\n" +
                    "FROM menucard\n" +
                    "INNER JOIN menuFilling ON menuFilling.pizzaId = ID\n" +
                    "GROUP BY menucard.id;";

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next())
            {
                int id = rs.getInt("menucard.id");
                String name = rs.getString("menucard.navn");
                double price = rs.getDouble("menucard.pris");
                String filling = rs.getString("fyld");
                String[] fillings = filling.split(",");

                Ordre tmpOrdre = null;
                tmpOrders.add(tmpOrdre);

            }
            st.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return tmpOrders;
    }


}