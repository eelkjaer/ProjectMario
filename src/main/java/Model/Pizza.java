/*
@author Jacob Lange Nielsen (cph-jn352@cphbusiness.dk)
 */
package Model;

import java.util.Arrays;


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

    public void setNumber(int number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setFilling(String[] filling) {
        this.filling = filling;
    }

    public int getNumber(){
        return this.number;
    }

    public double getPrice() {
        return price;
    }

    public String getFillingFormatted(){ //Retunerer en formatteret streng med toppings.
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
