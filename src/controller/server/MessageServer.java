package controller.server;

import controller.Controller;
import model.Message;
import model.MessageManager;
import model.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class MessageServer implements Runnable{


    private MessageManager messageManager;
    ConnectedClients connectedClients;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private ServerSocket serverSocket;
    public Thread server = new Thread(this);
    private Controller controller;
    private boolean running;


     public MessageServer (MessageManager messageManager, int port, Controller controller) {
         this.controller = controller;
         this.messageManager = messageManager;
         this.connectedClients = new ConnectedClients();
         try {
             serverSocket = new ServerSocket(port);
             System.out.println(serverSocket + " is now running!");
             running = true;
         } catch (IOException e) {
             running = false;
             return;
         }

         server.start();
     }

    /**
     * Waits for a new connection from a User. Gets the user's details, then starts a thread to listen for messages
     */
    @Override
    public void run() {
         while (true) {
             try {
                 Socket socket = serverSocket.accept();
                 ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                 User user = (User) ois.readObject();
                 String response = user.getUserName();
                 oos.writeObject(response);
                 oos.flush();
                 ClientHandler clientHandler = new ClientHandler(ois, oos, controller);
                 connectedClients.put(user, clientHandler);
                 clientHandler.start();

             } catch (IOException | ClassNotFoundException e) {
                 e.printStackTrace();
             }
         }


    }

    private class ClientHandler extends Thread {
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        private Controller controller;

        public ClientHandler(ObjectInputStream ois, ObjectOutputStream oos, Controller controller) {
            this.controller = controller;
            this.ois = ois;
            this.oos = oos;
        }

        @Override
        public void run() {

            while (true) {
                try {
                    Message message = (Message) ois.readObject();
                    sendToConnectedUsers(message);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }

        public void send(Message message) {
            try {
                oos.writeObject(message);
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public boolean isRunning() {
        return running;
    }

    private void sendToConnectedUsers(Message message) {

        User[] recipients = message.getRecipients();
        for (User user: recipients) {
                ClientHandler clientHandler = connectedClients.get(user);
                if (clientHandler!=null) {
                    clientHandler.send(message);
                    propertyChangeSupport.firePropertyChange("value", null, message.getSender().getUserName() + " to " + message.getRecipients()[0].getUserName() + ": " + message.getText());
                }
                else {
                    messageManager.storeMessage(user, message);
                    propertyChangeSupport.firePropertyChange("value", null, message.getSender().getUserName() + " to " + message.getRecipients()[0].getUserName() + " message stored");
                }
            }

        }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    private class ConnectedClients {

         private HashMap<User, ClientHandler> clients = new HashMap<User, ClientHandler>();

        public synchronized void put(User user, ClientHandler clientHandler) {
            clients.put(user, clientHandler);
        }

        public synchronized ClientHandler get(User user) {
            return clients.get(user);
        }
    }

}
