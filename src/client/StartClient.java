package client;

public class StartClient {

    public static void main(String[] args) {
        MessageClient messageClient = new MessageClient("localhost", 2555);
    }
}