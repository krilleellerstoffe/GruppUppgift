package view.panels;

import controller.Controller;
import model.LogFileManager;
import view.Viewer;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class ServerLog extends Viewer implements PropertyChangeListener {

    private Controller controller;
    private JPanel panel;

    public ServerLog(String title, int width, int height, Controller controller) {
        super(title, width, height);
        this.controller = controller;
        controller.messageServer.addPropertyChangeListener(this);
        add(content());
    }

    public ArrayList<String> getServerStringOutputs() {
        return controller.getServerFileManager().getStringFormatList();
    }


    public JPanel content() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        update();
        return panel;
    }

    public void update() {
        ArrayList<String> test = getServerStringOutputs();
        JList log = new JList(test.toArray());
        log.setFont(new Font("Serif", Font.BOLD, 14));
        log.setBackground(Color.lightGray);
        panel.add(log);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("value")) {
            LogFileManager log = controller.getServerFileManager();

            log.addLog((String) evt.getNewValue());
            update();

        }
    }
}
