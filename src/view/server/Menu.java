package view.server;

import controller.server.ServerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends Viewer {

    private JTextField ip;
    private JButton serverBtn;
    private JButton stopServerBtn;
    private ServerController controller;

    public Menu(String title, int width, int height, ServerController controller) {
        super(title, width, height);
        this.controller = controller;
        add(content());
    }

    @Override
    public JPanel content() {
        Action action = new Action(controller);
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        JPanel inner = new JPanel();

        inner.setPreferredSize(new Dimension(200,100));

        serverBtn = new JButton("Run Server");
        serverBtn.addActionListener(e -> {
            if (controller.serverStarts()) {
                Viewer server = new ServerLog("Server Log", 500, 400, controller);
                server.location(0, 300);
                server.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null, "Couldn't start server, might already be running.");
            }
        });
        inner.add(serverBtn);

        panel.add(inner, BorderLayout.SOUTH);
        return panel;
    }

    public class Action implements ActionListener {

        private ServerController controller;

        public Action(ServerController controller) {
            this.controller = controller;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(serverBtn)) {
                if (controller.serverStarts()) {
                    Viewer server = new ServerLog("Server Log", 500, 400, controller);
                    server.location(0, 300);
                    server.show();
                }else{
                    JOptionPane.showMessageDialog(null, "Couldn't start server, might already be running.");
                }
            }
        }
    }

}
