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
    private ArrayList<String> stats = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private final View view = new View(this);
    private final Menucard menucard = new Menucard();
    private SQLController sql = new SQLController("jdbc:mysql://104.248.135.65/mariodb", "java", "java", false);

    public MainController() throws ClassNotFoundException {

    }

    public void runApplication() throws FileNotFoundException {
        //customers = fh.readCustomersFromFile("Data/customers.csv"); //Importerer alle kunderne til memory.
        customers = sql.importCustomers();

        //menucard.createMenucard("Data/menu.csv"); //Importerer alle pizzaer til memory.
        menucard.setMenucard(sql.importPizzas());

        String statsFilePath = "Export/"+ LocalDate.now().toString() + "-statistik.csv";
        if(fh.doesFileExist(statsFilePath)) {
            stats = fh.readStatsFromFile(statsFilePath);
        }

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
            } else { //Hvis kunden ikke eksisterer, tastes ekstra info og kunden gemmes både i memory og i databasen.
                System.out.println("Kunde eksisterede ikke.");
                view.strInput("".trim()); //Fikser Scanner string bug
                String cName = view.strInput("Indtast kundenavn: ");
                String cMail = view.strInput("Indtast mail: ");
                tmpCust = new Customer(cName,cNum,cMail);
                customers.add(tmpCust);
            }
            fh.saveCustomersToFile(customers);
            break;
        }

        String orderComment;
        orderComment = view.strInput("Bemærkning til ordren (tom hvis ingen): ");

        if(orderComment.equals("") || orderComment.isEmpty() || orderComment.isBlank()){ //Sikre at variablen aldrig er null eller tom.
            orderComment = "Ingen bemærkninger";
        }

        /*
         * Hvis kunden afhenter ordren ligges der 1 time til nuværende klokkeslæt.
         * Såfremt kunden allerede står i butikken, ligges der 15 minutter til.
         */
        String inStoreQ = view.strInput("Står kunden i butikken? (Ja/Nej): ").toLowerCase();
        boolean inStore = inStoreQ.equals("ja");


        Ordre tmpOrder = new Ordre(inStore,tmpCust,tmpPizzaList,orderComment); //Opretter midlertidigt ordre objekt.


        orders.add(tmpOrder); //Tilføjer objektet til arrayet
        System.out.println(tmpOrder);

        fh.saveOrdersToFile(orders); //Gemmer ordren i ordrefilen.

        view.selectMenu();
    }

    /*
     * Ændre ordren til færdig, således den ikke vises i oversigten længere.
    */
    public void changeOrder(){
        int ordrenummer = view.intInput("Indtast ordrenummmer: ");

        for(Ordre o:orders){
            if(o.getOrderNumber() == ordrenummer){
                o.setDone(true);
            }
        }
        view.selectMenu();

    }

    /*
     * Viser alle ordre på skærmen.
     */
    public void getOrders(){
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

    /*
     * Opretter og udregner statistik for kunden.
     * Såfremt filen ikke findes oprettet en ny
     * Såfremt den findes, hentes data ind.
     */
    public void generateStats() throws FileNotFoundException {
        ArrayList<String> tmpStats = new ArrayList<>();

        double totalRev = 0.0;
        int[] taeller = new int[menucard.getMenu().size()];
        String filePath = "Export/" + LocalDate.now().toString() + "-statistik.csv";


        //NAVN - ANTAL SOLGTE - TOTAL OMSÆTNING
        int counter=0;

        if(fh.doesFileExist(filePath)){
            for(String s:stats){
                String[] data = s.split(";");
                totalRev+= Double.parseDouble(data[2]);
            }
        }
        for(Pizza pz:menucard.getMenu()){
            int pzCount = taeller[pz.getNumber()-1];

            String str = pz.getName() + ";" + pzCount + ";" + pzCount*pz.getPrice();

            if(fh.doesFileExist(filePath)){ //Hvis filen allerede eksisterer, skal den nuværende data erstattes.


                String[] data = stats.get(counter).split(";");
                int antal = pzCount+Integer.parseInt(data[1]);
                double pris = (pzCount*pz.getPrice()) + Double.parseDouble(data[2]);
                str = pz.getName() + ";" + antal + ";" + pris; //erstatter str således den gamle data også bliver brugt.
            }

            for(Ordre o:orders){
                if(!fh.doesFileExist(filePath)){ //Hvis filen ikke allerede eksisterer.
                    totalRev += o.getPrice(); //Tilføjer hver ordrens værdi til en samlet sum.
                }
                for(Pizza p:o.getPizzas()){
                    taeller[p.getNumber()-1]++;
                }
            }

            tmpStats.add(str); //Tilføjer datarække til array
            counter++;
        }

        fh.saveStatsToFile(tmpStats); //Gemmer arrayet i en CSV fil, så det kan åbnes i f.eks. Excel
        fh.saveOrdersToFile(orders); //Gemmer alle ordre i en CSV fil.

        System.out.printf("Total omsætning i dag: %.2f kr%n",totalRev);
        System.out.println("Statistik gemt: " + filePath+"\n");


        view.selectMenu();
    }
}
