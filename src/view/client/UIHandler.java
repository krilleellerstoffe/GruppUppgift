package view.client;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import client.ClientConsole;
import controller.client.ClientController;


public class UIHandler {

    private ClientController controller;
    private ClientConsole clientConsole;

    public UIHandler(ClientController controller) {
        this.controller = controller;

        JFrame login = new LoginFrame(this);
        login.setVisible(true);
    }

    public void showMainWindow() {
        clientConsole = new ClientConsole(controller);
    }

    public void logIn(String username, ImageIcon imageIcon) {
        //controller.login(username, imageIcon);
    }
}
