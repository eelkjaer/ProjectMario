package Model;

public class Pizza {
    int id;
    double price;

    public Pizza(int id){
        this.id = id;
        if(id==1){
            this.price = 57.0;
        } else if (id==2){
            this.price = 65.0;
        } else {
            this.price = 48.42;
        }
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                '}';
    }
}
