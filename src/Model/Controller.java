/*
@author Emil Elkjær Nielsen
 */
package Model;
import java.util.ArrayList;

public class Controller {
    private int menuSelect;
    private ArrayList<Integer> pizzas = new ArrayList<>();
    private View view = new View();

    public Controller(){

    };

    public void runApplication(){
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
                pizzas.add(pizza);
            }
        }
        System.out.println(pizzas);
    }

    private void changeOrder(){
        int ordrenummer = view.intInput("Indtast ordrenummmer");

        //TODO: Check om ordre eksisterer i arraylisten

    }

    private void getOrders(){
        System.out.println("Se ordre");
        //TODO: Print arraylist med åbne ordre.
    }

    private void generateStats(){
        System.out.println("Se statistik");
        //TODO: Eksporter ordre til csv fil.
    }


}
