package view.panels;

import view.Viewer;

import javax.swing.*;
import java.awt.*;

public class ServerConsole extends Viewer {


    public ServerConsole(String title, int width, int height) {
        super(title, width, height);
    }

    @Override
    public JPanel setContent() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel test = new JLabel("test");
        test.setText("testing");

        panel.add(test);

        return panel;
    }

}
