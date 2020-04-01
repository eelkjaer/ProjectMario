/*
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
 */
package View;
import java.util.Scanner;

public class View {

    private Scanner input = new Scanner(System.in);

    public int intInput(String inputRequired){
        System.out.print("\n" + inputRequired + ": ");
        return input.nextInt();
    }

    public String strInput(String inputRequired){
        System.out.print("\n" + inputRequired + ": ");
        input.nextLine();
        return input.nextLine();
    }

    protected double doubleInput(String inputRequired){
        System.out.print("\n" + inputRequired + ": ");
        return input.nextDouble();
    }


}
