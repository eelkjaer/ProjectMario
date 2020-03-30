package Model;

public class Pizza {
    int id;
    public Pizza(int id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                '}';
    }
}
