package Models;

public class FriendRequest {
    private User sender;
    private User receiver;

    public FriendRequest(User sender, User receiver){
        this.sender = sender;
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    @Override
    public String toString() {
        return  "{sender=" + sender +
                "receiver=" + receiver + "}";
    }

    //    public User sender(){
//        sender.sendFriendRequestTo(receiver);
//    }
}
