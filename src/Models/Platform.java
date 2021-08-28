package Models;

import java.util.ArrayList;

public class Platform {
    private static ArrayList<User> users = new ArrayList<>();
    public User register(String name, String email, String password) {
        User user = new User(name, email, password);
        users.add(user);

        return user;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public int getTotalNumberOfUser() {
        return users.size();
    }

    public boolean isLoggedOn(String username, String password) {
        for(User user: users){
            if(user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
    public static boolean contains(User user){
        for(User newUser: getUsers()){
            if((newUser.equals(user))){
                return true;
            }
        }
        return false;
    }

    public static void reset(){
        users.clear();
    }
}
