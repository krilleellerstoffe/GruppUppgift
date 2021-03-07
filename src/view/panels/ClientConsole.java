package view.panels;

import view.Viewer;

import javax.swing.*;

public class ClientConsole extends Viewer {

    public ClientConsole(String title, int width, int height) {
        super(title, width, height);
    }

    @Override
    public JPanel setContent() {
        JPanel panel = new JPanel();
        return panel;
    }

}
