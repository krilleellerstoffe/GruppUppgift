package view.panels;

import controller.Controller;
import model.LogFileManager;
import view.Viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class ServerLog extends Viewer implements PropertyChangeListener {

    private Controller controller;
    private JPanel panel;
    private JList log;
    private JScrollPane logScrollPane;

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
