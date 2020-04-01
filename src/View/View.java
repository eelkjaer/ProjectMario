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
        printMainMenu();
        int menuSelect = intInput("Dit valg");
        switch (menuSelect){
            case 1:
                ctrl.seeMenucard();
                break;
            case 2:
                ctrl.newOrder();
                break;
            case 3:
                ctrl.changeOrder();
                break;
            case 4:
                ctrl.getOrders();
                break;
            case 5:
                ctrl.generateStats();
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

    public int intInput(String inputRequired){
        System.out.print("\n" + inputRequired + ": ");
        return input.nextInt();
    }

    public String strInput(String inputRequired){
        System.out.print("\n" + inputRequired + ": ");
        return input.nextLine();
    }

    protected double doubleInput(String inputRequired){
        System.out.print("\n" + inputRequired + ": ");
        return input.nextDouble();
    }


}
