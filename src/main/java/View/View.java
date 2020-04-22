/*
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
 */
package View;
import java.util.Scanner;
import Controller.MainController;

public class View {
    private final Scanner input = new Scanner(System.in);
    private final MainController ctrl;

    public View(MainController control){
        this.ctrl = control;
    }

    public void selectMenu(){
        ctrl.refreshData();
        printMainMenu(); //Printer oversigt med menupunkter.
        int menuSelect = intInput("Dit valg: ");
        switch (menuSelect){ //Switch til menupunkter.
            case 1:
                System.out.println("## Se menukort ##");
                ctrl.seeMenucard();
                break;
            case 2:
                System.out.println("## Ny ordre ##");
                ctrl.newOrder();
                break;
            case 3:
                System.out.println("## Ændre ordre ##");
                ctrl.changeOrder();
                break;
            case 4:
                System.out.println("## Se ordre ##");
                ctrl.getOrders();
                break;
            case 5:
                System.out.println("\n### PIZZA STATISTIK ###");
                ctrl.generateStats();
                break;
            case 6:
                System.exit(0); //Lukker applikationen helt.
            default:
                System.out.println(menuSelect + " findes ikke!");
                selectMenu(); //Fortsætter loop hvis der vælges noget andet end mulighederne.
        }
    }

    private void printMainMenu(){
        System.out.println("\n#####################");
        System.out.println("1) Se menu");
        System.out.println("2) Opret ordre");
        System.out.println("3) Luk ordre");
        System.out.println("4) Se ordre");
        System.out.println("5) Se statistik");
        System.out.println("6) Afslut system");
        System.out.println("#####################\n");
    }

    public int intInput(String inputRequired){
        System.out.print("\n" + inputRequired);
        return input.nextInt();
    }

    public String strInput(String inputRequired){
        System.out.print("\n" + inputRequired);
        return input.nextLine();
    }

    protected double doubleInput(String inputRequired){
        System.out.print("\n" + inputRequired);
        return input.nextDouble();
    }


}
