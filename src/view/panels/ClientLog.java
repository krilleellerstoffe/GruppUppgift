package view.panels;

import view.Viewer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ClientLog extends Viewer {

    private JPanel panel;

    public ClientLog(String title, int width, int height) {
        super(title, width, height);
        add(content());
    }


    @Override
    public JPanel content() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        update();
        return panel;
    }

    public void update() {
        ArrayList<String> test = new ArrayList<>();
        JList log = new JList(test.toArray());
        log.setFont(new Font("Serif", Font.BOLD, 14));
        log.setBackground(Color.lightGray);
        panel.add(log);
    }
}
