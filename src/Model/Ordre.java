/*
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
 */
package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Ordre {
    private static int orderCounter = 202001;
    int orderNumber;
    boolean inStore;
    boolean isDone;
    LocalDateTime timestamp;
    Customer customer;
    ArrayList<Pizza> pizzas;
    double price;
    String comment;

    public Ordre(boolean inStore, Customer customer, ArrayList<Pizza> pizzas, String comment) {
        this.orderNumber = orderCounter;
        this.inStore = inStore;
        this.timestamp = calculateTime(this.inStore);
        this.customer = customer;
        this.pizzas = pizzas;
        this.isDone = false;
        this.price = calculatePrice(this.pizzas);
        this.comment = comment;
        orderCounter++;
    }

    public double calculatePrice(ArrayList<Pizza> pizzas){
        double totalPrice = 0.0;
        for(Pizza p:pizzas){
            totalPrice += p.getPrice();
        }
        return totalPrice;
    }

    public LocalDateTime calculateTime(boolean inStore){
        LocalDateTime now = LocalDateTime.now();
        if(inStore){
            return now.plusMinutes(15);
        } else {
            return now.plusHours(1);
        }
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public String toString() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        return "\nOrdre " + orderNumber
                + "\nIn store: " + inStore
                + "\nKlar tid: " + timestamp.format(format)
                + "\nKunde: " + customer
                + "\nPizzaer: " + pizzas
                + "\nPris: " + String.format("%.2f kr",price)
                + "\nKommentar: " + comment
                + "\n";
    }
}
