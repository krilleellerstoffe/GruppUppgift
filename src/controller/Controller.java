package controller;


import controller.client.MessageClient;
import controller.server.MessageServer;
import model.MessageManager;
import model.LogFileManager;
import view.Viewer;
import view.panels.Menu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;

public class Controller {

    private LogFileManager serverFileManager;
    private MessageClient messageClient;
    public MessageServer messageServer;
    private Viewer menu;

    public Controller() {

        serverFileManager = new LogFileManager("files/log.dat");
        serverFileManager.addLog("Server started");

        menu = new Menu("Program Selection Menu", 400, 400, this);
        menu.show();
    }

    public Viewer getMenu() {
        return menu;
    }

    public boolean serverStarts() {
        messageServer = new MessageServer(new MessageManager(), 2555, this);

        if (messageServer.isRunning()) {
            return true;
        }

        return false;
        /*LogFileManager lfm = new LogFileManager("files/log.dat");
        lfm.addLog("A log");
        System.out.println(System.currentTimeMillis());
        for (Log log: lfm.readLogFile(System.currentTimeMillis()-10000l, System.currentTimeMillis())) { //tests for logs made within last 10 seconds
            System.out.println(log);
        }*/
    }

    public boolean connect(String ip) {

        messageClient = new MessageClient(ip, 2555);
        if (messageClient.isConnected() || messageClient.isAvailable()) {
            return true;
        }
        return false;
    }

    public LogFileManager getServerFileManager() {
        return serverFileManager;
    }
}
