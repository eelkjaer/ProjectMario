/*
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
 */
package View;
import java.util.Scanner;

public class View {
    private final Scanner input = new Scanner(System.in);


    public View() {
    }

    public void printMsg(String msg){
        System.out.println(msg);
    }

    public int selectMenu(boolean admin){
        printMainMenu(admin); //Printer oversigt med menupunkter.
        int menuSelect = intInput("Dit valg: ");
        return menuSelect;
    }

    public void printMenuTitle(String title){
        System.out.println("## " + title + " ##");
    }

    private void printMainMenu(boolean admin){
        System.out.println("\n#####################");
        if(admin){
            System.out.println("1) Se menu");
            System.out.println("2) Opret ordre");
            System.out.println("3) Afslut ordre");
            System.out.println("4) Se åbne ordre");
            System.out.println("5) Se statistik");
            System.out.println("6) Log ud");
        } else {
            System.out.println("1) Se menu");
            System.out.println("2) Opret ordre");
            System.out.println("3) Afslut ordre");
            System.out.println("4) Log ud");
        }
        System.out.println("#####################\n");
    }

    public int intInput(String inputRequired){
        System.out.print("\n" + inputRequired);
        return Integer.parseInt(input.nextLine());
    }

    public String strInput(String inputRequired){
        System.out.print("\n" + inputRequired);
        return input.nextLine();
    }

    public double doubleInput(String inputRequired){
        System.out.print("\n" + inputRequired);
        return Double.parseDouble(input.nextLine());
    }


}
