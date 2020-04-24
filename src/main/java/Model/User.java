package Model;

public class User {
    String username;
    String password;
    String name;
    int level; //1 = mario, 2 = alfonso

    public User(String username, String password, String name, int level){
        this.username = username;
        this.password = password;
        this.name = name;
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }
}
