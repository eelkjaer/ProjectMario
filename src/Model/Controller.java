/*
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
 */
package Model;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Controller {
    private int menuSelect;
    private FileHandler fh = new FileHandler();
    private ArrayList<Ordre> orders = new ArrayList<>();
    private View view = new View();
    private Menucard menucard = new Menucard();

    public Controller() {

    };


    public void runApplication() throws FileNotFoundException {
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
        String cName = view.strInput("Indtast kundenavn");
        int cNum = view.intInput("Indtast tlf");
        String cMail = view.strInput("Indtast mail");

        Customer tmpCust = new Customer(cName,cNum,cMail);

        Ordre tmpOrder = new Ordre(false,tmpCust,tmpPizzaList,"None");

        orders.add(tmpOrder);
        System.out.println(tmpOrder);

        fh.fileWriter(orders);

        selectMenu();
    }

    private void changeOrder(){
        System.out.println("## Ændre ordre ##");
        int ordrenummer = view.intInput("Indtast ordrenummmer");

        for(Ordre o:orders){
            if(o.orderNumber==ordrenummer){
                o.setDone(true);
            }
        }

        selectMenu();

        //TODO: Check om ordre eksisterer i arraylisten

    }

    private void getOrders(){
        System.out.println("## Se ordre ##");
        if(orders.isEmpty()){
            System.out.println("Der er ingen åbne bestillinger!");
        } else {
            for(Ordre o:orders){
                if(!o.isDone){
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
            totalRev += o.price;
        }
        System.out.printf("Total omsætning i dag: %.2f kr%n",totalRev);

        selectMenu();

        //TODO: Eksporter ordre til csv fil.
    }
}
