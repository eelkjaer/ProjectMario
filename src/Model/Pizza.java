package Model;

import java.util.Arrays;

public class Pizza {
    int id;
    String name;
    double price;
    String[] fillings;

    public Pizza(int id, String name, double price,String[] fillings){
        this.id = id;
        this.name = name;
        this.price = price;
        this.fillings = fillings;

    }

    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", fillings=" + Arrays.toString(this.fillings) +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String[] getFillings() {
        return fillings;
    }

    public String[] setFillings(String[] fillings) {
        fillings = new String[]{"tomatsauce","ost","skinke","oregano","oksefars","pepperoni","kødsauce","spaghetti","cocktailpølser","bacon","rød peber","løg","oliven","ananas","champignon","kebab","chili","rejer"};
        return fillings;

    }
}
