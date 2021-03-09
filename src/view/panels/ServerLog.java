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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class ServerLog extends Viewer implements PropertyChangeListener, ActionListener {

    private Controller controller;
    private JPanel panel;
    private JList log;
    private JPanel panel1;
    private BorderLayout layout;
    private JButton go;
    private JComboBox date;
    private long time;
    private JScrollPane scrollPane;

    public ServerLog(String title, int width, int height, Controller controller) {
        super(title, width, height);
        this.controller = controller;
        controller.messageServer.addPropertyChangeListener(this);
        time = System.currentTimeMillis();
        add(content(), layout.PAGE_START);
    }

    public JPanel content() {
        panel = new JPanel();
        layout = new BorderLayout();
        panel.setLayout(layout);

        panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ArrayList<String> days = new ArrayList<>();

        ArrayList<Date> dates = controller.getLogDates();
        for (int i = 0; i < dates.size(); i++) {
            days.add(dates.get(i) + "");
        }

        date = new JComboBox(days.toArray());
        panel1.add(date);
        log = new JList();
        scrollPane = new JScrollPane();
        log.setLayoutOrientation(JList.VERTICAL);
        scrollPane.setViewportView(log);

        go = new JButton("GO");
        go.addActionListener(this);
        panel1.add(go);
        add(scrollPane);
        panel.add(panel1);
        update();
        return panel;
    }


    public void update() {
        ArrayList<String> messages = controller.getStringFormatList(time);
        Collections.reverse(messages);
        log.setListData(messages.toArray());
        log.setFont(new Font("Serif", Font.BOLD, 14));
        log.setBackground(Color.lightGray);
        panel.add(log, layout.SOUTH);
        scrollPane.setViewportView(log);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("value")) {
            LogFileManager log = controller.getServerFileManager();
            log.addLog((String) evt.getNewValue());
            update();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String strDate = (String) date.getSelectedItem();

        try {
            Date date1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(strDate);
            time = System.currentTimeMillis() - date1.getTime();
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }

        update();
    }

}
