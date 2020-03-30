package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Ordre {
    private static int orderCounter = 202001;
    int orderNumber;
    boolean inStore;
    LocalDateTime timestamp;
    Customer customer;
    ArrayList<Pizza> pizzas;
    double price;
    String comment;

    public Ordre(int orderNumber, boolean inStore, Customer customer, ArrayList<Pizza> pizzas, double price, String comment) {
        this.orderNumber = orderCounter;
        this.inStore = inStore;
        this.timestamp = calculateTime(this.inStore);
        this.customer = customer;
        this.pizzas = pizzas;
        this.price = calculatePrice(this.pizzas);
        this.comment = comment;
        orderCounter++;
    }

    public double calculatePrice(ArrayList<Pizza> pizzas){
        for(Pizza p:pizzas){
            //TODO: Sum price of array
        }
        return 0.0;
    }

    public LocalDateTime calculateTime(boolean inStore){
        LocalDateTime now = LocalDateTime.now();
        if(inStore){
            return now.plusMinutes(15);
        } else {
            return now.plusHours(1);
        }
    }
}
