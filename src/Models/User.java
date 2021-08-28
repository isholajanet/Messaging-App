package Models;

import java.util.ArrayList;
import java.util.List;

import Exception.UserNotFoundException;
import Exception.UnregisteredUserException;

public class User {
    private String username;
    private String email;
    private String password;
    private List<User> friends = new ArrayList<User>();
    private ArrayList <FriendRequest> sentFriendRequest = new ArrayList<>();
    private ArrayList <FriendRequest> receivedFriendRequest = new ArrayList<>();


    public User(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public User searchForFriends(String username) throws UserNotFoundException {
        ArrayList<User> users = new ArrayList<>();
        User user = new User(this.username, email,password);
        users.add(user);
        for(User myUser: users){
            if (!(myUser.getUsername().equals(username))){
                throw new UserNotFoundException(username + "not found");
            }
        }
        return user;
    }

    public void sendFriendRequestTo(User receiver) throws UnregisteredUserException {
        FriendRequest request = new FriendRequest(this, receiver);

            if(Platform.contains(request.getReceiver()) && Platform.contains(request.getSender())) {
                this.sentFriendRequest.add(request);
                receiver.receivedFriendRequest.add(request);
            }else{
                throw new UnregisteredUserException("You are not a registered user");
            }

    }

    public int getTotalNumberOfSentFriendRequest() {
        return friends.size();
    }

    public int getFriendRequestList() {
       return sentFriendRequest.size();
    }

    public boolean isFriendsWith(User user)
     {
         return friends.contains(user);
    }

    public int getPendingSentRequests() {
        return sentFriendRequest.size();
    }

    public int getPendingReceivedRequests() {
        return receivedFriendRequest.size();
    }

    @Override
    public String toString() {
        return  "username='" + username + "\n" +
                "email='" + email + '\n' +
                "number of friends = " + friends.size();
    }

    public int getTotalNumberOfFriends() {
       return friends.size();
    }

    public void acceptFriendRequest(User sender) {
        for(FriendRequest request: receivedFriendRequest){
            if(request.getSender().equals(sender)){
                friends.add(sender);
                sender.sentFriendRequest.remove(request);
                sender.friends.add(request.getReceiver());
                receivedFriendRequest.remove(request);
                break;
            }
        }
    }
}
