package Model;
import Data.UserMapper;

public class UserHandler {
    private final UserMapper mapper = new UserMapper();

    public User login(String usr, String psw){
        return mapper.checkLogin(usr,psw);


    }

}
