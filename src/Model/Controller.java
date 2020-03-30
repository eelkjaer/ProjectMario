/*
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
 */
package Model;
import java.util.ArrayList;

public class Controller {
    private int menuSelect;
    private ArrayList<Pizza> pizzas = new ArrayList<>();
    private ArrayList<Ordre> orders = new ArrayList<>();
    private View view = new View();

    public Controller(){

    };

    private void test(){
        ArrayList<Pizza> tmpPizza = new ArrayList<>();
        tmpPizza.add(new Pizza(1));
        tmpPizza.add(new Pizza(2));

        //Laver test ordre;
        Ordre ordre1 = new Ordre(true,null,tmpPizza,"Ordre 1");
        Ordre ordre2 = new Ordre(true,null,tmpPizza,"Ordre 2");
        Ordre ordre3 = new Ordre(false,null,tmpPizza,"Ordre 3");

        //Sætter ordre som afhentet/færdige
        ordre3.isDone = true;

        //Sætter ordre i array
        orders.add(ordre1);
        orders.add(ordre2);
        orders.add(ordre3);
    }

    public void runApplication(){
        test();
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
            default:
                selectMenu();
        }
    }

    private void printMainMenu(){
        System.out.println("1) Se menu");
        System.out.println("2) Opret ordre");
        System.out.println("3) Ændre ordre");
        System.out.println("4) Se ordre");
        System.out.println("5) Se statistik");
    }

    private void seeMenucard(){
        System.out.println("Se menukort");
        //TODO: Print arraylist med tilgængelige pizzzaer
    }

    private void newOrder(){
        System.out.println("Indtast ønskede pizza nummer. Afsluttes med 0");
        boolean ispizza = true;
        while(ispizza){
            int pizza = view.intInput("Pizza nr");
            if(pizza == 0){
                ispizza = false;
            } else {
                pizzas.add(new Pizza(pizza));
            }
        }
        Ordre tmpOrder = new Ordre(false,null,pizzas,"None");
        System.out.println(tmpOrder);
    }

    private void changeOrder(){
        int ordrenummer = view.intInput("Indtast ordrenummmer");

        //TODO: Check om ordre eksisterer i arraylisten

    }

    private void getOrders(){
        System.out.println("Se ordre");
        for(Ordre o:orders){
            if(!o.isDone){
                System.out.println(o);
            }
        }
    }

    private void generateStats(){
        System.out.println("Se statistik");
        //TODO: Eksporter ordre til csv fil.
    }


}
