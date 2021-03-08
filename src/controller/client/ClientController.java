package controller.client;

import model.User;
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

    private static final String FILEPATH_CONTACTS = "files\\contacts.dat";
    private static final String FILEPATH_CONTACTS_FOLDER = "files";

    private ArrayList<User> contacts;
    private ArrayList<User> connectedUsers;
    private MessageClient messageClient;
    public User user;

    public ClientController() {
        messageClient = new MessageClient(SERVERADDRESS, PORT);
        connectedUsers = new ArrayList<User>();
        contacts = new ArrayList<User>();
        readContactsFromFile();
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

        for (User u : users) {
            contacts.add(u);
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

    public void updateConnectedList(List<User> list) {
        connectedUsers.clear();
        for (User u : list) {
            connectedUsers.add(u);
        }
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
}
