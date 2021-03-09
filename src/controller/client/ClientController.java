package controller.client;

import controller.client.MessageClient;
import model.User;
import UI.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class ClientController {

    private static final String SERVERADDRESS = "localhost";
    private static final int PORT = 2555;

    private static final String FILEPATH_CONTACTS = "LogFile/contacts.dat";
    private static final String FILEPATH_CONTACTS_FOLDER = "LogFile";

    private ArrayList<User> contacts;
    private ArrayList<User> connectedUsers;
    private MessageClient messageClient;
    public User user;
    private String userName;
    private ClientConsole ui;
    private UIHandler UI;

    public ClientController() {
        messageClient = new MessageClient(SERVERADDRESS, PORT);
        connectedUsers = new ArrayList<User>();
        contacts = new ArrayList<User>();
        readContactsFromFile();
        messageClient.setClientController(this);
        messageClient.addProperChangeListener(ui);
        UI = new UIHandler(this);
    }

    //Read contacts from file, run on startup.
    private void readContactsFromFile() {
        File folders = new File(FILEPATH_CONTACTS_FOLDER);
        if (!folders.exists()) {
            folders.mkdirs();
        }

        File contact = new File(FILEPATH_CONTACTS);
        if (contact.isFile()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(contact));
                while (true) {
                    User u = (User) ois.readObject();
                    contacts.add(u);

                }
            } catch (EOFException EOFE) {
                // Exception is thrown and caught here when all user objects are read.
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("contacts file did not exist");
        }
    }

    /**
     * Method that writes the contacts to the filepath.
     * @param users
     */
    public void writeContacts(ArrayList<User> users) {

        contacts.clear();

        for (User c : users) {
            contacts.add(c);
        }

        File oldContacts = new File(FILEPATH_CONTACTS);
        oldContacts.delete();

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(FILEPATH_CONTACTS)));
            for (User u : users) {
                oos.writeObject(u);
            }
            oos.flush();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateConnectedList(ArrayList<User> users) {
        connectedUsers.clear();
        for (User u : users) {
            connectedUsers.add(u);
        }
        ui.updateConnectedList(connectedUsers);
    }

    public ArrayList<User> getConnectedUsers() {
        return connectedUsers;
    }
    public ArrayList<User> getContacts() {
        return contacts;
    }

    public static void main(String[] args){
        new ClientController();
    }

    public void disconnectClient() {
        messageClient.disconnect();
    }

    public void login(String username, ImageIcon img) {
        user = new User(username, img);
      //  MessageServer.connect(user);   // Fixa
    }

    public void sendMessage(String text, String fileName, String[] reciever) {
        //Message message = new Message(text, new ImageIcon(fileName), reciever, userName);
        //messageClient.send(message);
    }

    public void setUserName(String userName) {
        this.userName = userName;

    }
}
