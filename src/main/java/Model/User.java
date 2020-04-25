package Model;

public class User {
    String username;
    String password;
    String name;
    boolean isAdmin;

    public User(String username, String password, String name, boolean isAdmin){
        this.username = username;
        this.password = password;
        this.name = name;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPassword(){
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
