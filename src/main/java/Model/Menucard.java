/*
@author Jacob Lange Nielsen (cph-jn352@cphbusiness.dk)
 */
package Model;
import Controller.FileHandler;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Menucard {
    private ArrayList<Pizza> menu = new ArrayList<>();
    private final FileHandler fh = new FileHandler();

    public void setMenucard(ArrayList<Pizza> pizzas){
        this.menu = pizzas;
    }

    public ArrayList<Pizza> getMenu() {
        return this.menu;
    }

    public void printMenu(){
        for(Pizza p:menu){
            System.out.println( //Printer et formatteret menukort.
                    p.getNumber()
                            + ". " + p.getName()
                            + ": " + p.getFillingFormatted()
                            + "...... " + p.getPrice()
                            + ",-");
        }
    }

    public Pizza getPizzaById(int pizzaId) {
        Pizza foundPizza = null;
        for (Pizza p:menu) {
            if(p.getNumber() == pizzaId){
                foundPizza = p;
                break;
            }
        }
        return foundPizza;
    }

    public void createMenucard(String filePath) throws FileNotFoundException {
        setMenucard(fh.readMenuFromFile(filePath)); //Opretter et menu array fra filen.

    }

}


