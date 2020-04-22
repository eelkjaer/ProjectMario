/*
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
 */
package Controller;
import Data.CustomerMapper;
import Data.MenuMapper;
import Data.OrdreMapper;
import Model.*;
import View.View;
import java.util.ArrayList;

public class MainController {

    private ArrayList<Ordre> orders = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private final View view = new View(this);
    private final Menucard menucard = new Menucard();
    private final OrdreMapper ordreMapper = new OrdreMapper();


    public MainController() {

    }

    public void refreshData(){
        customers = new CustomerMapper().getAllCustomers();
        menucard.setMenucard(new MenuMapper().getMenucard());
        orders = ordreMapper.getAllOrders(customers, menucard.getMenu());
    }

    public void runApplication(){
        refreshData();

        view.selectMenu(); //Starter menu loop.
    }

    public void seeMenucard(){
        menucard.printMenu(); //Printer hele menukortet

        view.selectMenu(); //Viser user menuen igen.
    }

    /*
     * Opretter nyt ordre objekt og tilføjer til ordrelisten og ordre filen.
     */
    public void newOrder(){
        ArrayList<Pizza> tmpPizzaList = new ArrayList<>();
        System.out.println("Indtast ønskede pizza nummer. Afsluttes med 0");
        boolean ispizza = true;
        while(ispizza){ //Loop indtil der tastes 0
            int number = view.intInput("Pizza nr: ");
            if(number == 0){ //Afslutter loopet
                ispizza = false;
            } else if (number > menucard.getMenu().size()) { //Fejl hvis nummeret ikke eksisterer
                System.out.println("Det indtastede nummer eksisterer ikke!");
            } else{
                tmpPizzaList.add(menucard.getMenu().get(number-1));
            }
        }

        int cNum = view.intInput("Indtast kundenummer (Tlf): ");

        Customer tmpCust = null;
        for(Customer c: customers){
            if(c.getPhoneNo() == cNum){ //Hvis kunden eksisterer/Allerede er oprettet, bruges dette kunde objekt
                System.out.println("Kunde eksisterer allerede: " + c.getName());
                tmpCust = c;
                tmpCust.addNewOrder();
                view.strInput("".trim()); //Fikser Scanner string bug
                break;
            }
        }
        if(tmpCust == null){
            //Hvis kunden ikke eksisterer, tastes ekstra info og kunden gemmes både i memory og i databasen.
            System.out.println("Kunde eksisterede ikke.");
            view.strInput("".trim()); //Fikser Scanner string bug
            String cName = view.strInput("Indtast kundenavn: ");
            String cMail = view.strInput("Indtast mail: ");
            tmpCust = new Customer(cName,cNum,cMail);
            customers.add(tmpCust);
            new CustomerMapper().createNewCustomer(tmpCust);
        }

        String orderComment;
        orderComment = view.strInput("Bemærkning til ordren (tom hvis ingen): ");

        if(orderComment.equals("") || orderComment.isEmpty()){ //Sikre at variablen aldrig er null eller tom.
            orderComment = "Ingen bemærkninger";
        }

        /*
         * Hvis kunden afhenter ordren ligges der 1 time til nuværende klokkeslæt.
         * Såfremt kunden allerede står i butikken, ligges der 15 minutter til.
         */
        String inStoreQ = view.strInput("Står kunden i butikken? (Ja/Nej): ").toLowerCase();
        boolean inStore = inStoreQ.equals("ja");


        Ordre tmpOrder = new Ordre(inStore,tmpCust,tmpPizzaList,orderComment); //Opretter midlertidigt ordre objekt.


        orders.add(ordreMapper.createNewOrder(tmpOrder)); //Tilføjer objektet til db og derefter arrayet
        System.out.println(tmpOrder);

        view.selectMenu();
    }

    /*
     * Ændre ordren til færdig, således den ikke vises i oversigten længere.
    */
    public void changeOrder(){
        int ordrenummer = view.intInput("Indtast ordrenummmer: ");

        for(Ordre o:orders){
            if(o.getOrderNumber() == ordrenummer){
                ordreMapper.setAsDone(o);
            }
        }

        view.selectMenu();
    }

    /*
     * Viser alle ordre på skærmen.
     */
    public void getOrders(){
        orders.clear();
        orders = ordreMapper.getAllOrders(customers,menucard.getMenu());
        if(orders.isEmpty()){
            System.out.println("Der er ingen bestillinger!");
        } else {
            for(Ordre o:orders){
                if(!o.isDone()){
                    System.out.println(o);
                } else {
                    //System.out.println("'Ordre " + o.getOrderNumber() + "' er færdig!");
                }
            }
        }

        view.selectMenu();
    }

    /*
     * Opretter og udregner statistik for kunden.
     * Såfremt filen ikke findes oprettet en ny
     * Såfremt den findes, hentes data ind.
     */
    public void generateStats() {
        ordreMapper.getPizzaStats("salg");

        view.selectMenu();
    }

}
