package server;

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
    private Thread server = new Thread(this);


     public MessageServer (MessageManager messageManager, int port) {

         this.messageManager = messageManager;
         this.connectedClients = new ConnectedClients();
         try {
             serverSocket = new ServerSocket(port);
         } catch (IOException e) {
             e.printStackTrace();
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
                 ClientHandler clientHandler = new ClientHandler(ois, oos);
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

        public ClientHandler(ObjectInputStream ois, ObjectOutputStream oos) {

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

    private void sendToConnectedUsers(Message message) {

        User[] recipients = message.getRecipients();
        for (User user: recipients) {
                ClientHandler clientHandler = connectedClients.get(user);
                if (clientHandler!=null) {
                    clientHandler.send(message);
                }
                else {
                    messageManager.storeMessage(user, message);
                }
            }

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
