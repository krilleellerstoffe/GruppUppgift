package controller;

import controller.client.MessageClient;
import controller.server.MessageServer;
import model.Log;
import model.MessageManager;
import model.LogFileManager;
import view.Viewer;
import view.panels.Menu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;

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

    public ArrayList<String> getStringFormatList(long time) {
        ArrayList<String> slog = new ArrayList<>();
        ArrayList<String> all = new ArrayList<>();
        if (time == 0) {
            for (Log log : serverFileManager.readLogFile()) {
                slog.add(log.toString());
            }
            return slog;
        } else {
            for (Log log : serverFileManager.readLogFile(System.currentTimeMillis() - time, System.currentTimeMillis())) { //tests for logs made within last 10 seconds
                all.add(log.toString());
            }
            return all;
        }
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

    public MessageServer getMessageServer() {
        return messageServer;
    }
}
