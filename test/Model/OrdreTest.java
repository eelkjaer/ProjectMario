package Model;

import Controller.FileHandler;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


class OrdreTest {
Ordre ordre;
Customer customer;
Pizza pizza1;
Pizza pizza2;

    @Before
    public void setUp() {
       // boolean inStore, Customer customer, ArrayList<Pizza> pizzas, String comment
        // String name, int phoneNo, String mail
        // int number, String name, double price, String[] filling
        String name = "kurt";
        int phone = 22338114;
        String mail = "andreas@gmail.com";
        ArrayList<Pizza> pizzas = new ArrayList<>();


        customer = new Customer(name, phone, mail, 1);

        pizza1 = new Pizza(3,"Cacciatore",57, new String[] {"tomatsauce","ost","pepperoni","oregano"});
        pizza2 = new Pizza(4,"Cabona",63, new String[] {"tomatsauce","ost","kødsauce","spaghetti","cocktailpølser","oregano"});

        pizzas.add(pizza1);
        pizzas.add(pizza2);
        ordre = new Ordre(true,customer,pizzas,"done");

    }

    @Test
    public void getPrice() {
        double expected = 120;
        double actual = ordre.getPrice();

        assertEquals(expected, actual);
    }
}