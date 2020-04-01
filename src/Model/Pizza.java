/*
@author Jacob Lange Nielsen (cph-jn352@cphbusiness.dk)
 */
package Model;

import java.util.Arrays;

//Constructer

public class Pizza {
    private int number;
    private String name;
    private double price;
    private String[] filling;

    public Pizza(int number, String name, double price, String[] filling){
        this.number = number;
        this.name = name;
        this.price = price;
        this.filling = filling;

    }
//getter and toString
    public int getNumber(){
        return this.number;
    }

    public double getPrice() {
        return price;
    }

    public String getFillingFormatted(){
        return Arrays.toString(this.filling).replace("[","").replace("]","");
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "\n" + number + ". " + name + ": " + Arrays.toString(filling) + "\n";
    }
}
