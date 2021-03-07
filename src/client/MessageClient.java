package client;

import server.Message;
import server.User;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class MessageClient implements Runnable{

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private boolean connected;

    public MessageClient (String ipAddress, int port) {
        try {
            socket = new Socket(ipAddress, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        String userName = JOptionPane.showInputDialog("Enter Username");
        User user = new User(userName);
        try {
            oos.writeObject(user);
            oos.flush();
            String response = (String) ois.readObject();
            JOptionPane.showMessageDialog(null, response+ " connected");
            connected = true;
            String input = JOptionPane.showInputDialog("Enter message");
            String to = JOptionPane.showInputDialog("To whom?");
            User[] recipients = new User[1];
            recipients[0] = new User(to);
            send(new Message(input, user, recipients));
            listen();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            connected = false;
        }
        while (true){

        }
    }
    public void listen() {
        while (true){
            Message message = null;
            try {
                message = (Message) ois.readObject();
                JOptionPane.showMessageDialog(null, message.getText());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
    public void send (Message message) {
        try {
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isConnected() {
        return connected;
    }
}
