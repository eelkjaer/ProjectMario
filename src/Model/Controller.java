/*
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
 */
package Model;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Controller {
    private int menuSelect;
    private FileHandler fh = new FileHandler();
    private ArrayList<Pizza> pizzas = new ArrayList<>();
    private ArrayList<Ordre> orders = new ArrayList<>();
    private View view = new View();
    private Menucard menucard = new Menucard();

    public Controller() {

    };

    /*private void test(){
        ArrayList<Pizza> tmpPizza = new ArrayList<>();
        tmpPizza.add(new Pizza(1));
        tmpPizza.add(new Pizza(2));
        tmpPizza.add(new Pizza(3));
        tmpPizza.add(new Pizza(4));

        //Laver test ordre;
        Ordre ordre1 = new Ordre(true,null,tmpPizza,"Ordre 1");
        Ordre ordre2 = new Ordre(false,null,tmpPizza,"Ordre 2");
        tmpPizza.remove(3);
        Ordre ordre3 = new Ordre(false,null,tmpPizza,"Ordre 3");

        //Sætter ordre som afhentet/færdige
        ordre2.isDone = true;

        //Sætter ordre i array
        orders.add(ordre1);
        orders.add(ordre2);
        orders.add(ordre3);
    }*/

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
        System.out.println("Se menukort");

        //TODO: Print arraylist med tilgængelige pizzzaer
        System.out.println(menucard.getMenu());
        selectMenu();
    }

    private void newOrder(){
        System.out.println("Indtast ønskede pizza nummer. Afsluttes med 0");
        System.out.println(menucard.getMenu().size());
        boolean ispizza = true;
        while(ispizza){
            int number = view.intInput("Pizza nr");
            if(number == 0){
                ispizza = false;
            } else if (number > menucard.getMenu().size()) {
                System.out.println("Det indtastede nummer eksisterer ikke!");
            } else{
                pizzas.add(menucard.getMenu().get(number-1));
            }
        }
        Ordre tmpOrder = new Ordre(false,null,pizzas,"None");
        orders.add(tmpOrder);
        System.out.println(tmpOrder);
        pizzas.clear();


        //TEST
        fh.fileWriter(orders);

        selectMenu();
    }

    private void changeOrder(){
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
        System.out.println("Se ordre");
        if(orders.isEmpty() || orders == null || orders.size() == 0){
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
        System.out.println("Se statistik");
        double totalRev = 0.0;

        for(Ordre o:orders){
            totalRev += o.price;
        }
        System.out.printf("Total omsætning i dag: %.2f kr%n",totalRev);

        selectMenu();

        //TODO: Eksporter ordre til csv fil.
    }


}
