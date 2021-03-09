package view.server;

import controller.server.ServerController;
import model.LogFileManager;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class ServerLog extends Viewer implements PropertyChangeListener {

    private ServerController controller;
    private JPanel panel;
    private JList log;
    private JScrollPane logScrollPane;

    public ServerLog(String title, int width, int height, ServerController controller) {
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
        createComponents();
        return panel;
    }

    public void createComponents() {
        log = new JList();
        logScrollPane = new JScrollPane(log);
        log.setFont(new Font("Serif", Font.BOLD, 14));
        log.setBackground(Color.lightGray);
        panel.add(logScrollPane);
        updateList();
    }
    private void updateList () {
        log.setListData(getServerStringOutputs().toArray());
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("value")) {
            LogFileManager log = controller.getServerFileManager();

            log.addLog((String) evt.getNewValue());
            updateList();

        }
    }
}
