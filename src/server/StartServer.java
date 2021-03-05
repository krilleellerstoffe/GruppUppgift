package server;

import client.MessageClient;

public class StartServer {
    public static void main(String[] args) {
        MessageServer messageServer = new MessageServer(new MessageManager(), 2555);
    }
}
