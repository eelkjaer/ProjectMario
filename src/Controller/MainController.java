/*
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
 */
package Controller;
import Model.*;
import View.View;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class MainController {
    private int menuSelect;
    private FileHandler fh = new FileHandler();
    private ArrayList<Ordre> orders = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private View view = new View();
    private Menucard menucard = new Menucard();

    public MainController() {

    }


    public void runApplication() throws FileNotFoundException {
        customers = fh.readCustomersFromFile("Data/customers.csv");
        menucard.createMenucard("Data/menu.csv");
        selectMenu();
    }


    private void selectMenu(){
        printMainMenu();
        this.menuSelect = new View().intInput("Dit valg");
        switch (this.menuSelect){
            case 1:
                seeMenucard();
                break;
            case 2:
                newOrder();
                break;
            case 3:
                changeOrder();
                break;
            case 4:
                getOrders();
                break;
            case 5:
                generateStats();
                break;
            case 6:
                System.exit(0);
            default:
                selectMenu();
        }
    }

    private void printMainMenu(){
        System.out.println("#####################");
        System.out.println("1) Se menu");
        System.out.println("2) Opret ordre");
        System.out.println("3) Luk ordre");
        System.out.println("4) Se ordre");
        System.out.println("5) Se statistik");
        System.out.println("6) Afslut system");
        System.out.println("#####################");
    }

    private void seeMenucard(){
        System.out.println("## Se menukort ##");

        System.out.println(menucard.getMenu());
        selectMenu();
    }


    private void newOrder(){
        ArrayList<Pizza> tmpPizzaList = new ArrayList<>();
        System.out.println("## Ny ordre ##");
        System.out.println("Indtast ønskede pizza nummer. Afsluttes med 0");
        boolean ispizza = true;
        while(ispizza){
            int number = view.intInput("Pizza nr");
            if(number == 0){
                ispizza = false;
            } else if (number > menucard.getMenu().size()) {
                System.out.println("Det indtastede nummer eksisterer ikke!");
            } else{
                tmpPizzaList.add(menucard.getMenu().get(number-1));
            }
        }

        int cNum = view.intInput("Indtast kundenummer (Tlf)");

        Customer tmpCust = null;
        for(Customer c: customers){
            if(c.getPhoneNo() == cNum){
                System.out.println("Kunde eksisterer allerede: " + c.getName());
                tmpCust = c;
                tmpCust.addNewOrder();
                fh.saveCustomersToFile(customers);
                break;
            } else {
                System.out.println("Kunde eksisterede ikke.");
                String cName = view.strInput("Indtast kundenavn");
                String cMail = view.strInput("Indtast mail");
                tmpCust = new Customer(cName,cNum,cMail);
                customers.add(tmpCust);
                fh.saveCustomersToFile(customers);
                break;
            }
        }


        String orderComment = view.strInput("Bemærkning til ordren (tom hvis ingen)");

        if(orderComment.equals("") || orderComment.isEmpty() || orderComment.isBlank()){
            orderComment = "Ingen bemærkninger";
        }

        boolean inStore;
        String inStoreQ = view.strInput("Afhentes i butikken? (Ja/Nej)").toLowerCase();
            if(inStoreQ.equals("ja")){
                inStore = true;
            } else if (inStoreQ.equals("nej")){
                inStore = false;
            } else {
                inStore = false;
            }


        Ordre tmpOrder = new Ordre(false,tmpCust,tmpPizzaList,orderComment);


        orders.add(tmpOrder);
        System.out.println(tmpOrder);

        fh.saveOrdersToFile(orders);

        selectMenu();
    }

    private void changeOrder(){
        System.out.println("## Ændre ordre ##");
        int ordrenummer = view.intInput("Indtast ordrenummmer");

        for(Ordre o:orders){
            if(o.getOrderNumber()==ordrenummer){
                o.setDone(true);
            }
        }
        selectMenu();

    }

    private void getOrders(){
        System.out.println("## Se ordre ##");
        if(orders.isEmpty()){
            System.out.println("Der er ingen åbne bestillinger!");
        } else {
            for(Ordre o:orders){
                if(!o.isDone()){
                    System.out.println(o);
                }
            }
        }
        selectMenu();
    }

    private void generateStats(){
        System.out.println("## Se statistik ##");
        double totalRev = 0.0;

        for(Ordre o:orders){
            totalRev += o.getPrice();
        }
        System.out.printf("Total omsætning i dag: %.2f kr%n",totalRev);

        int occurrences = 0;

        

        for(Ordre o:orders){
            occurrences = Collections.frequency(o.getPizzas(), "Cabona");
            
        }

        System.out.println("count: " + occurrences);

        selectMenu();

        //TODO: Eksporter ordre til csv fil.
    }
}
