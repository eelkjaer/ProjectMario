/*
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
 */
package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Ordre {
    private static int orderCounter = 202001;
    private int orderNumber;
    private boolean inStore;
    private boolean isDone;
    private LocalDateTime timestamp;
    private Customer customer;
    private ArrayList<Pizza> pizzas;
    private double price;
    private String comment;
    private String createdBy;

    public Ordre(boolean inStore, Customer customer, ArrayList<Pizza> pizzas, String comment, String createdBy) {
        this.orderNumber = orderCounter;
        this.inStore = inStore;
        this.timestamp = calculateTime(this.inStore);
        this.customer = customer;
        this.pizzas = pizzas;
        this.isDone = false;
        this.price = calculatePrice(this.pizzas);
        this.comment = comment;
        this.createdBy = createdBy;
        orderCounter++;
    }

    //From MySQL
    public Ordre(int orderNumber, boolean inStore, boolean isDone, LocalDateTime timestamp, Customer customer, ArrayList<Pizza> pizzas, double price, String comment, String createdBy) {
        this.orderNumber = orderNumber;
        this.inStore = inStore;
        this.isDone = isDone;
        this.timestamp = timestamp;
        this.customer = customer;
        this.pizzas = pizzas;
        this.price = price;
        this.comment = comment;
        this.createdBy = createdBy;
    }

    /*
     * Udregner ordrens samlet værdi ved at bruge prisen for hver pizza.
     */
    public double calculatePrice(ArrayList<Pizza> pizzas){
        double totalPrice = 0.0;
        for(Pizza p:pizzas){
            totalPrice += p.getPrice();
        }
        return totalPrice;
    }

    /*
     * Udregner klokkeslættet hvor ordren er færdig.
     * Hvis i butikken: 15 minutter efter bestilling
     * Hvis afhentning: 1 time efter bestilling
     */
    public LocalDateTime calculateTime(boolean inStore){
        LocalDateTime now = LocalDateTime.now();
        if(inStore){
            return now.plusMinutes(15);
        } else {
            return now.plusHours(1);
        }
    }

    public void setOrderNumber(int id){
        this.orderNumber = id;
    }
    public int getOrderNumber() {
        return orderNumber;
    }

    public boolean isInStore() {
        return inStore;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }


    public String getComment() {
        return comment;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public double getPrice() {
        return price;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        String type;
        if(inStore){
            type="I butikken";
        } else {
            type="Afhentning";
        }

        if(customer==null){ //Failsafe hvis der sker anormale ændringer i databasen
            return "\nOrdre " + orderNumber
                    + "\nType: " + type
                    + "\nKlar tid: " + timestamp.format(format)
                    + "\nKunde: Anonym"
                    + "\nPizzaer: " + pizzas
                    + "\nPris: " + String.format("%.2f kr",price)
                    + "\nKommentar: " + comment
                    + "\n";
        }

        return "\nOrdre " + orderNumber
                + "\nType: " + type
                + "\nKlar tid: " + timestamp.format(format)
                + "\nKunde: " + customer.getName() + ", " + customer.getPhoneNo() + " - Mail: " + customer.getMail() + " - Tidligere ordre: " + customer.getPrevOrder()
                + "\nPizzaer: " + pizzas
                + "\nPris: " + String.format("%.2f kr",price)
                + "\nKommentar: " + comment
                + "\n";
    }
}
