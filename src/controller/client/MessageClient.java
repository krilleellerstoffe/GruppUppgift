package controller.client;

import controller.client.ClientController;
import model.Message;
import model.User;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MessageClient implements Runnable {

    private Socket socket;
    private ClientController controller;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private boolean connected;
    private boolean isAvailable;
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public MessageClient (String ipAddress, int port) {
        try {
            socket = new Socket(ipAddress, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect(User user) {
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            controller.setUserName(user.getUserName());
            oos.writeObject(user);
            oos.flush();
            isAvailable = true;
            String response = (String) ois.readObject();
            JOptionPane.showMessageDialog(null, response);
            connected = true;
        } catch (IOException | ClassNotFoundException e) {
            isAvailable = false;
            return;
        }
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {

        while (true) {
            Message message;
            try {
                message = (Message) ois.readObject();
                if (message.getSender().getUserName().equals("Server")) {
                    User[] connectedUsers = message.getRecipients();
                    changes.firePropertyChange("ConnectedUsers", null, connectedUsers);
                } else {
                    changes.firePropertyChange("message", null, message);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
    public void listen() {

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

    public boolean isAvailable() {
        return isAvailable;
    }

  public void disconnect() {
      if (connected) {
          try {
              socket.close();
          } catch (IOException ioException) {
              ioException.printStackTrace();
          }
      }
  }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }

    public void setClientController(ClientController controller) {
        this.controller = controller;
    }
}
