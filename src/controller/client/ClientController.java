package client;

import controller.client.MessageClient;
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
import java.util.List;

public class ClientController {

    private static final String SERVERADDRESS = "localhost";
    private static final int PORT = 2555;

    private static final String FILEPATH_CONTACTS = "LogFile/contacts.dat";
    private static final String FILEPATH_CONTACTS_FOLDER = "LogFile";

    private ArrayList<Contact> contacts;
    private ArrayList<User> connectedUsers;
    private MessageClient messageClient;
    public User user;
    private String userName;
    private ClientConsole ui = new ClientConsole(this);

    public ClientController() {
        messageClient = new MessageClient(SERVERADDRESS, PORT);
        connectedUsers = new ArrayList<User>();
        contacts = new ArrayList<Contact>();
        readContactsFromFile();
        messageClient.setClientController(this);
        JFrame frame = new JFrame();
        frame.setTitle("Chat console");
        frame.setBounds(100,100,820,600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(ui);
        messageClient.addProperChangeListener(ui);
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
                    Contact c = (Contact) ois.readObject();
                    contacts.add(c);

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
    public void writeContacts(ArrayList<Contact> users) {

        contacts.clear();

        for (Contact c : users) {
            contacts.add(c);
        }

        File oldContacts = new File(FILEPATH_CONTACTS);
        oldContacts.delete();

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(FILEPATH_CONTACTS)));
            for (Contact c : users) {
                oos.writeObject(c);
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
    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public static void main(String[] args){
        new ClientController();
    }

    public void disconnectClient() {
        messageClient.disconnect();
    }

    public void sendMessage(String text, String fileName, String[] reciever) {
        //Message message = new Message(text, new ImageIcon(fileName), reciever, userName);
        //messageClient.send(message);
    }

    public void setUserName(String userName) {
        this.userName = userName;

    }
}
