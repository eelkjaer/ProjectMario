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

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "\nPizza{"
                + "\nnumber=" + number
                + "\nname=" + name
                + "\nprice=" + price
                + "\nfilling=" + Arrays.toString(filling)
                + "\n}";
    }
}
