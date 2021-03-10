package view.panels;

import view.Viewer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class Login extends Viewer {

    private JPanel panel;
    private JPanel avatarPane;

    private JPanel contentPane;
    private JTextField txtUsername;
    private JButton btnLogIn = new JButton("Log in");
    private JButton btnChooseImage = new JButton("Choose image");

    private ImageIcon avatar;
    private String username;

    public Login(String title, int width, int height) {
        super(title, width, height);
        add(content());
    }

    @Override
    public JPanel content() {
        panel = new JPanel();
        //clientConsole = new JPanel();
        panel.setLayout(new BorderLayout());
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        txtUsername = new JTextField();
        txtUsername.setToolTipText("Your username");
        txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
        txtUsername.setBounds(236, 235, 200, 30);
        contentPane.add(txtUsername);
        txtUsername.setColumns(10);

        this.addWindowListener( new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
                txtUsername.requestFocus();
            }
        });

        //Same as in clicking the "log in" button
        Action action = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (txtUsername.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(null, "Username must be longer", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    username = txtUsername.getText();
                    //uiHandler.logIn(username, avatar);
                    //uiHandler.showMainWindow();
                    dispose();
                }
            }
        };

        txtUsername.addActionListener(action);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(300, 210, 75, 15);
        contentPane.add(lblUsername);

        btnLogIn.setBounds(289, 385, 89, 23);
        btnLogIn.addActionListener(new Login.ButtonListener());
        contentPane.add(btnLogIn);

        avatarPane = new JPanel();
        avatarPane.setBorder(new LineBorder(new Color(0, 0, 0)));
        avatarPane.setBounds(278, 11, 112, 113);
        contentPane.add(avatarPane);

        btnChooseImage.setBounds(275, 135, 125, 23);
        btnChooseImage.addActionListener(new Login.ButtonListener());
        contentPane.add(btnChooseImage);
        return panel;
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == btnChooseImage) {
                JFileChooser JFC = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
                JFC.setFileFilter(filter);
                int optionpicked = JFC.showOpenDialog(null);
                if (optionpicked == JFileChooser.APPROVE_OPTION) {
                    File file = JFC.getSelectedFile();
                    avatar = new ImageIcon(file.getPath());
                    avatarPane.removeAll();
                    avatarPane.add(new JLabel(avatar));
                    avatarPane.updateUI();
                }
            }
            if (e.getSource() == btnLogIn) {
                if (txtUsername.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(null, "Username must be longer", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    username = txtUsername.getText();
                    //uiHandler.logIn(username, avatar);
                    //uiHandler.showMainWindow();
                    dispose();
                }
            }
        }
    }
}
