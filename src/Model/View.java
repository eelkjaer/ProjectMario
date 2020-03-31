/*
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
 */
package Model;
import java.util.Scanner;

public class View {

    private Scanner input = new Scanner(System.in);

    protected int intInput(String inputRequired){
        System.out.print("\n" + inputRequired + ": ");
        return input.nextInt();
    }

    protected String strInput(String inputRequired){
        System.out.print("\n" + inputRequired + ": ");
        input.nextLine();
        String str = input.nextLine();
        return str;
    }

    protected double doubleInput(String inputRequired){
        System.out.print("\n" + inputRequired + ": ");
        return input.nextDouble();
    }


}
