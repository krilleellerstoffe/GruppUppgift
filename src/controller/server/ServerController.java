package controller.server;

import controller.server.MessageServer;
import model.MessageManager;
import model.LogFileManager;
import view.Viewer;
import view.panels.Menu;


public class ServerController {

    private LogFileManager serverFileManager;
    public MessageServer messageServer;
    private Viewer menu;

    public ServerController() {

        serverFileManager = new LogFileManager("files/log.dat");
        serverFileManager.addLog("Server started");

        menu = new Menu("Program Selection Menu", 400, 400, this);
        menu.setVisible(true);
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
    }

    public LogFileManager getServerFileManager() {
        return serverFileManager;
    }

    public static void main(String[] args) {
        new ServerController();
    }
}
