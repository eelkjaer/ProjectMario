/*
@author Jacob Lange Nielsen (frækfyr@cphbusiness.dk)
 */
package Model;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Menucard {
    private ArrayList<Pizza> menu = new ArrayList<>();
    private FileHandler fh = new FileHandler();

    public void setMenucard(ArrayList<Pizza> pizzas){
        this.menu = pizzas;
    }

    public ArrayList<Pizza> getMenu() {
        return this.menu;
    }

    public void createMenucard(String filePath) throws FileNotFoundException {
        setMenucard(fh.readMenuFromFile(filePath));

    }
}


