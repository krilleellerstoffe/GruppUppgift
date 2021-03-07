package model;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageManager {

    private HashMap<User, ArrayList<Message>> storedMessages = new HashMap<User, ArrayList<Message>>();


    public synchronized void storeMessage(User user, Message message) {

        if(get(user)==null){
            put(user, new ArrayList<>());
        }
        get(user).add(message);

    }
    public synchronized ArrayList<Message> get(User user) {
        return storedMessages.get(user);
    }

    public synchronized void put(User user, ArrayList<Message> messages) {
        storedMessages.put(user, messages);
    }
    public synchronized void remove(User user) {
        storedMessages.remove(user);
    }
}
