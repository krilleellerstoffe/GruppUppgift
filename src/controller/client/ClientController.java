package controller.client;

import view.client.ClientConsole;
import view.client.UIHandler;
import model.Message;
import model.User;

import javax.swing.*;
import java.util.ArrayList;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientController {

    private static final String SERVERADDRESS = "localhost";
    private static final int PORT = 2555;

    private static final String FILEPATH_CONTACTS = "LogFile/contacts.dat";
    private static final String FILEPATH_CONTACTS_FOLDER = "LogFile";

    private ArrayList<User> contacts;
    private ArrayList<User> connectedUsers;
    private MessageClient messageClient;
    public User user;
    private ClientConsole ui;
    private UIHandler UI;

    public ClientController() {
        messageClient = new MessageClient(SERVERADDRESS, PORT);
        connectedUsers = new ArrayList<User>();
        contacts = new ArrayList<User>();
        readContactsFromFile();
        messageClient.setClientController(this);
        messageClient.addPropertyChangeListener(ui);
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
        System.exit(0);
    }

    public void sendMessage(String text, String fileName, String[] recipients) {
        User[] recipientList = new User[100];
        for (int i = 0; i < recipients.length; i++) {
            User u = new User(recipients[i]);
            recipientList[i] = u;
        }
        Message message = new Message(text, new ImageIcon(fileName), user, recipientList);
        messageClient.send(message);
    }
    public void sendMessage(String text, String[] recipients) {
        User[] recipientList = new User[100];
        for (int i = 0; i < recipients.length; i++) {
            User u = new User(recipients[i]);
            recipientList[i] = u;
        }
        Message message = new Message(text, user, recipientList);
        messageClient.send(message);
    }

}
