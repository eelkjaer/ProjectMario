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

    private User user;
    private ArrayList<Ordre> orders = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private final View view = new View();
    private final Menucard menucard = new Menucard();
    private final UserHandler users = new UserHandler();
    private final OrdreMapper ordreMapper = new OrdreMapper();
    private final MenuMapper menuMapper = new MenuMapper();

    private boolean isLoggedIn = false;


    public MainController() {
        refreshData();
    }

    public void refreshData(){
        customers = new CustomerMapper().getAllCustomers();
        menucard.setMenucard(new MenuMapper().getMenucard());
        orders = ordreMapper.getAllOrders(customers, menucard.getMenu());
    }

    public void runApplication(){
        refreshData();

        userLogin();
    }

    public void userLogin(){
        view.printMsg("Velkommen til Just-Pizza!");
        while(!isLoggedIn){
            String username = view.strInput("Indtast brugernavn: ");
            String password = view.strInput("Indtast password: ");

            User usr = users.login(username,password);
            if(usr != null){
                user = usr;
                view.printMsg("\nVelkommen tilbage " + user.getName());
                selectMenu();
                isLoggedIn = true;
            } else {
                view.printMsg("Forkert login! Prøv igen.");
            }
        }

    }

    public void userLogOff(){
        clearScreen();
        user = null;
        isLoggedIn = false;
        userLogin();
    }

    private boolean checkAdminLevel(){
        return user.isAdmin();
    }

    public void selectMenu(){
        refreshData();
        int menuSelect = view.selectMenu(checkAdminLevel()); //Starter menu loop.

        createMenu(menuSelect, checkAdminLevel());
    }

    public void createMenu(int menuSelect, boolean admin){
        if(admin){
            switch (menuSelect){ //Switch til menupunkter.
                case 1:
                    seeMenucard();
                    break;
                case 2:
                    newPizza();
                    break;
                case 3:
                    newOrder();
                    break;
                case 4:
                    changeOrder();
                    break;
                case 5:
                    getOrders();
                    break;
                case 6:
                    generateStats();
                    break;
                case 7:
                    newUser();
                    break;
                case 8:
                    userLogOff();
                    //System.exit(0); //Lukker applikationen helt.
                default:
                    view.printMsg(menuSelect + " er ikke et gyldigt valg!");
                    selectMenu();
            }
        } else {
            //Non admin
            switch (menuSelect){
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
                    userLogOff();
                    //System.exit(0); //Lukker applikationen helt.
                default:
                    view.printMsg(menuSelect + " er ikke et gyldigt valg!");
                    selectMenu();
            }
        }
    }

    public void seeMenucard(){
        view.printMenuTitle("Menukort");
        menucard.printMenu(); //Printer hele menukortet

        selectMenu(); //Viser user menuen igen.
    }

    public void newPizza(){
        view.printMenuTitle("Ny pizza");

        //Pizza navn
        String pizzaName = "";
        while(pizzaName.isEmpty()) {
            pizzaName = view.strInput("Pizzaens navn: ");
        }
        //Pizza fyld
        ArrayList<String> tmpPizzaFyld = new ArrayList<>();
        System.out.println("Indtast pizza fyld. Afsluttes med 0");
        boolean istopping = true;
        while(istopping){ //Loop indtil der tastes 0
            String fyld = view.strInput("Fyld: ");
            if(fyld.equals("0")){ //Afslutter loopet
                if(!tmpPizzaFyld.isEmpty()){ //Sikre at der minimum er valgt 1 pizza.
                    istopping = false;
                } else {
                    System.out.println("Der er ikke indtastet noget fyld.");
                }
            } else{
                tmpPizzaFyld.add(fyld);
            }
        }

        //Pizza pris
        double pizzaPrice;
        pizzaPrice = view.doubleInput("Pizzaens pris: ");

        Pizza tmpPizza = new Pizza(pizzaName,pizzaPrice,tmpPizzaFyld); //Opretter midlertidigt pizza objekt.


        menucard.getMenu().add(menuMapper.createNewPizza(tmpPizza));

        view.printMsg(tmpPizza.getName() + " er tilføjet!");

        selectMenu();
    }


    public void newOrder(){
        view.printMenuTitle("Ny ordre");
        ArrayList<Pizza> tmpPizzaList = new ArrayList<>();
        System.out.println("Indtast ønskede pizza nummer. Afsluttes med 0");
        boolean ispizza = true;
        while(ispizza){ //Loop indtil der tastes 0
            int number = view.intInput("Pizza nr: ");
            if(number == 0){ //Afslutter loopet
                if(!tmpPizzaList.isEmpty()){ //Sikre at der minimum er valgt 1 pizza.
                    ispizza = false;
                } else {
                    System.out.println("Der er ikke valgt nogle pizzaer.");
                }
            } else if (number > menucard.getMenu().size()) { //Fejl hvis nummeret ikke eksisterer
                System.out.println("Det indtastede nummer eksisterer ikke!");
            } else{
                tmpPizzaList.add(menucard.getMenu().get(number-1));
            }
        }

        int cNum = 0;
        while(cNum <= 0) {
            cNum = view.intInput("Indtast kundenummer (Tlf): ");
        }

        Customer tmpCust = null;
        for(Customer c: customers){
            if(c.getPhoneNo() == cNum){ //Hvis kunden eksisterer/Allerede er oprettet, bruges dette kunde objekt
                System.out.println("Kunde eksisterer allerede: " + c.getName());
                tmpCust = c;
                tmpCust.addNewOrder();
                break;
            }
        }
        if(tmpCust == null){
            //Hvis kunden ikke eksisterer, tastes ekstra info og kunden gemmes både i memory og i databasen.
            System.out.println("Kunde eksisterede ikke.");
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
        String inStoreQ = "";
        while(inStoreQ.isEmpty()) {
            inStoreQ = view.strInput("Står kunden i butikken? (Ja/Nej): ").toLowerCase();
        }
        boolean inStore = inStoreQ.equals("ja");

        String createdBy = user.getName();

        Ordre tmpOrder = new Ordre(inStore,tmpCust,tmpPizzaList,orderComment, createdBy); //Opretter midlertidigt ordre objekt.

        orders.add(ordreMapper.createNewOrder(tmpOrder)); //Tilføjer objektet til db og derefter arrayet
        System.out.println(tmpOrder);

        selectMenu();
    }

    /*
     * Ændre ordren til færdig, således den ikke vises i oversigten længere.
    */
    public void changeOrder(){
        view.printMenuTitle("Luk ordre");
        int ordrenummer = view.intInput("Indtast ordrenummmer: ");

        for(Ordre o:orders){
            if(o.getOrderNumber() == ordrenummer){
                ordreMapper.setAsDone(o);
            }
        }

        selectMenu();
    }

    /*
     * Viser alle ordre på skærmen.
     */
    public void getOrders(){
        view.printMenuTitle("Se åbne ordre");
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

        selectMenu();
    }

    /*
     * Opretter og udregner statistik for kunden.
     * Såfremt filen ikke findes oprettet en ny
     * Såfremt den findes, hentes data ind.
     */
    public void generateStats() {
        view.printMenuTitle("Se statistik");
        System.out.println("Sorter efter: (Standard: Efter omsætning)");
        System.out.println("1) Efter omsætning");
        System.out.println("2) Efter antal solgte");
        System.out.println("3) Efter medarbejder");
        int select = view.intInput("Indtast dit valg: ");
        if(select==1){
            ordreMapper.getPizzaStats("salg");
        } else if (select==2) {
            ordreMapper.getPizzaStats("antal");
        } else if (select==3) {
            ordreMapper.getPizzaStats("medarbejder");
        } else {
            ordreMapper.getPizzaStats("salg");
        }

        selectMenu();
    }

    public void newUser(){
        view.printMenuTitle("Ny bruger");

        //Sætter username
        String userName = "";
        while(userName.isEmpty()) {
            userName = view.strInput("Indtast brugernavn: ");
        }

        //Sætter fornavn
        String firstName = "";
        while(firstName.isEmpty()) {
            firstName = view.strInput("Indtast fornavn: ");
        }

        //Kodeord
        String password = "";
        while(password.isEmpty()) {
            password = view.strInput("Indtast kodeord: ");
        }

        //Er bruger admin?
        String isAdminQ = "";
        while(isAdminQ.isEmpty()) {
            isAdminQ = view.strInput("Er brugeren administrator? (Nej/Ja): ").toLowerCase();
        }
        boolean isAdmin = isAdminQ.equals("ja");

        User tmpUser = new User(userName,password,firstName,isAdmin);

        users.register(tmpUser);
        view.printMsg(tmpUser.getName() + " er tilføjet!");

        selectMenu();
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
