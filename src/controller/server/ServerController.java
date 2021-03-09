package controller.server;

import model.MessageManager;
import model.LogFileManager;
import view.server.Viewer;
import view.server.Menu;


public class ServerController {

    private LogFileManager serverFileManager;
    public MessageServer messageServer;
    private Viewer menu;

    public ServerController() {

        serverFileManager = new LogFileManager("files/log.dat");
        serverFileManager.addLog("Server started");

        menu = new Menu("Program Selection Menu", 200, 100, this);
        menu.setVisible(true);
    }

    public Viewer getMenu() {
        return menu;
    }

    public boolean serverStarts() {
        messageServer = new MessageServer(new MessageManager(), 2555);

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
