/*
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
 */
package Controller;
import Model.*;
import View.View;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MainController {

    private final FileHandler fh = new FileHandler();
    private final ArrayList<Ordre> orders = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private final View view = new View(this);
    private final Menucard menucard = new Menucard();

    public MainController() {

    }

    public void runApplication() throws FileNotFoundException {
        customers = fh.readCustomersFromFile("Data/customers.csv"); //Importerer alle kunderne til memory.
        menucard.createMenucard("Data/menu.csv"); //Importerer alle pizzaer til memory.

        view.selectMenu(); //Starter menu loop.
    }

    public void seeMenucard(){
        System.out.println("## Se menukort ##");

        for(Pizza p:menucard.getMenu()){
            System.out.println(
                    p.getNumber()
                    + ". " + p.getName()
                    + ": " + p.getFillingFormatted()
                    + "......" + p.getPrice()
                    + ",-");
        }

//        String menu = menucard.getMenu().toString()
//                .replace(",","")
//                .replace("[","")
//                .replace("]","")
//                .trim()
//                .;
//        System.out.println(menu);
        view.selectMenu(); //Viser user menuen igen.
    }

    /*
     *
     */
    public void newOrder(){
        ArrayList<Pizza> tmpPizzaList = new ArrayList<>();
        System.out.println("## Ny ordre ##");
        System.out.println("Indtast ønskede pizza nummer. Afsluttes med 0");
        boolean ispizza = true;
        while(ispizza){
            int number = view.intInput("Pizza nr: ");
            if(number == 0){
                ispizza = false;
            } else if (number > menucard.getMenu().size()) {
                System.out.println("Det indtastede nummer eksisterer ikke!");
            } else{
                tmpPizzaList.add(menucard.getMenu().get(number-1));
            }
        }

        int cNum = view.intInput("Indtast kundenummer (Tlf): ");

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
                String cName = view.strInput("Indtast kundenavn: ");
                String cMail = view.strInput("Indtast mail: ");
                tmpCust = new Customer(cName,cNum,cMail);
                customers.add(tmpCust);
                fh.saveCustomersToFile(customers);
                break;
            }
        }
        view.strInput("".trim()); //Fikser Scanner string bug

        String orderComment;
        orderComment = view.strInput("Bemærkning til ordren (tom hvis ingen): ");

        if(orderComment.equals("") || orderComment.isEmpty() || orderComment.isBlank()){
            orderComment = "Ingen bemærkninger";
        }

        boolean inStore = false;
        String inStoreQ = view.strInput("Afhentes i butikken? (Ja/Nej): ").toLowerCase();
        inStore = inStoreQ.equals("ja");


        Ordre tmpOrder = new Ordre(inStore,tmpCust,tmpPizzaList,orderComment);


        orders.add(tmpOrder);
        System.out.println(tmpOrder);

        fh.saveOrdersToFile(orders);

        view.selectMenu();
    }

    public void changeOrder(){
        System.out.println("## Ændre ordre ##");
        int ordrenummer = view.intInput("Indtast ordrenummmer: ");

        for(Ordre o:orders){
            if(o.getOrderNumber() == ordrenummer){
                o.setDone(true);
            }
        }
        view.selectMenu();

    }

    public void getOrders(){
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
        view.selectMenu();
    }

    public void generateStats(){
        System.out.println("\n### PIZZA STATISTIK ###");

        ArrayList<String> stats = new ArrayList<>();
        double totalRev = 0.0;
        int[] taeller = new int[menucard.getMenu().size()];
        String filePath = "Export/" + LocalDate.now().toString() + "-statistik.csv";

        for(Ordre o:orders){
            totalRev += o.getPrice();
            for(Pizza p:o.getPizzas()){
                taeller[p.getNumber()-1]++;
            }
        }

        //System.out.println("NAVN - ANTAL SOLGTE - TOTAL OMSÆTNING");
        for(Pizza pz:menucard.getMenu()){
            int pzCount = taeller[pz.getNumber()-1];
            String str = pz.getName() + ";" + pzCount + ";" + pzCount*pz.getPrice();
            stats.add(str);
            //System.out.println(str.replace(";"," - "));
        }

        fh.saveStatsToFile(stats);
        fh.saveOrdersToFile(orders);

        System.out.printf("Total omsætning i dag: %.2f kr%n",totalRev);
        System.out.println("Statistik gemt: " + filePath+"\n");


        view.selectMenu();
    }
}
