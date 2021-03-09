package view.panels;

import controller.Controller;
import model.LogFileManager;
import view.Viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class ServerLog extends Viewer implements PropertyChangeListener {

    private Controller controller;
    private JPanel panel;
    private BorderLayout layout;
    private JButton go;
    private JComboBox date;
    private long time;

    public ServerLog(String title, int width, int height, Controller controller) {
        super(title, width, height);
        this.controller = controller;
        controller.messageServer.addPropertyChangeListener(this);
        time = 0;
        add(content());
    }

    public JPanel content() {
        panel = new JPanel();
        layout = new BorderLayout();
        panel.setLayout(layout);
        update();
        return panel;
    }
    private JList log;

    public void update() {
        ArrayList<String> days = new ArrayList<>();
        days.add("1000");

        JPanel panel1 = new JPanel(new GridLayout(0,6,0,0));
        Action action = new Action();
        date = new JComboBox(days.toArray());

        panel1.add(date);
        go = new JButton("GO");
        go.addActionListener(action);
        panel1.add(go);

        panel.add(panel1, layout.PAGE_START);

        ArrayList<String> test = controller.getStringFormatList(time);
        log = new JList();
        log.removeAll();
        log.setListData(test.toArray());
        log.setFont(new Font("Serif", Font.BOLD, 14));
        log.setBackground(Color.lightGray);
        panel.add(log, layout.PAGE_END);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("value")) {
            LogFileManager log = controller.getServerFileManager();
            log.addLog((String) evt.getNewValue());
            update();
        }
    }

    public class Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
                long l = Long.valueOf((String)date.getSelectedItem());
                time = l;
                update();
        }
    }
}
