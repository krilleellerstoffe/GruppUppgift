package UI.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.client.ClientController;
import model.User;

public class MRecipientsFrame extends JFrame {

    private JPanel contentPane;

    private JList<String> listConnected;
    private JList<String> listContacts;
    private JList<String> listRecipients;

    private ClientController controller;

    private JFrame thisWindow = this;

    private JButton btnDone;
    private JButton btnAdd;
    private JButton btnRemove;

    private HashMap<String,User> userMap;
    private ArrayList<User> recipients;

    public MRecipientsFrame(ClientController controller) {
        this.controller = controller;
        init();
        initLists();
    }

    private void init() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1000, 700);
        setResizable(false);
        setVisible(true);
        setBounds(100, 100, 1000, 700);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        ListListener listListener = new ListListener();

        JLabel lblNewLabel = new JLabel("Set which users are to receive the message in the list to the right");
        contentPane.add(lblNewLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0, 0, 0)));
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 237, 615);
        panel.add(scrollPane);

        listConnected = new JList<String>();
        listConnected.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listConnected.addListSelectionListener(listListener);
        scrollPane.setViewportView(listConnected);

        JLabel lblConnected = new JLabel("Connected Users");
        lblConnected.setHorizontalAlignment(SwingConstants.CENTER);
        scrollPane.setColumnHeaderView(lblConnected);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(270, 11, 237, 615);
        panel.add(scrollPane_1);

        listContacts = new JList<String>();
        listContacts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listContacts.addListSelectionListener(listListener);
        scrollPane_1.setViewportView(listContacts);

        JLabel lblContacts = new JLabel("Contacts");
        lblContacts.setHorizontalAlignment(SwingConstants.CENTER);
        scrollPane_1.setColumnHeaderView(lblContacts);

        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(600, 11, 237, 615);
        panel.add(scrollPane_2);

        listRecipients = new JList<String>();
        listRecipients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listRecipients.addListSelectionListener(listListener);
        scrollPane_2.setViewportView(listRecipients);

        JLabel lblRecipients = new JLabel("Recipients");
        lblRecipients.setHorizontalAlignment(SwingConstants.CENTER);
        scrollPane_2.setColumnHeaderView(lblRecipients);

        btnDone = new JButton("Done");
        btnDone.setBounds(845, 587, 119, 39);
        btnDone.addActionListener(new ButtonListener());
        panel.add(btnDone);

        btnAdd = new JButton("Add");
        btnAdd.setBounds(507, 300, 93, 50);
        btnAdd.addActionListener(new ButtonListener());
        panel.add(btnAdd);

        btnRemove = new JButton("Remove");
        btnRemove.setBounds(507, 350, 93, 50);
        btnRemove.addActionListener(new ButtonListener());
        panel.add(btnRemove);
    }

    private void initLists() {
        ArrayList<User> connected = controller.getConnectedUsers();
        ArrayList<User> contacts  = controller.getContacts();

        ArrayList<String> connectedString = new ArrayList<String>();
        ArrayList<String> contactsString = new ArrayList<String>();

        recipients = new ArrayList<User>();
        userMap = new HashMap<String,User>();

        for(User u : connected) {
            userMap.put(u.getUserName(), u);
            connectedString.add(u.getUserName());
        }

        for(User u : contacts) {
            userMap.put(u.getUserName(), u);
            contactsString.add(u.getUserName());
        }

        for(User u : contacts) {
            if(connectedString.contains(u.getUserName())) {
                connectedString.remove(u.getUserName());
            }
        }

        DefaultListModel<String> modelConnected = new DefaultListModel<String>();
        DefaultListModel<String> modelContacts = new DefaultListModel<String>();

        for(String s : connectedString) {
            modelConnected.addElement(s);
        }
        for(String s : contactsString) {
            modelContacts.addElement(s);
        }

        listConnected.setModel(modelConnected);
        listContacts.setModel(modelContacts);
    }

    private class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == btnDone) {
                recipients.clear();

                ListModel<String> model = listRecipients.getModel();

                for (int i = 0; i < model.getSize(); i++) {
                    User u = userMap.get(model.getElementAt(i));
                    recipients.add(u);
                }
  //            Ska Fixas

                thisWindow.dispose();
            }

            if(e.getSource() == btnAdd) {

                if(listRecipients.getSelectedIndex() != -1) {
                    JOptionPane.showMessageDialog(null,"Must select in the list of connected contacts, not recipients.", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    if(listConnected.getSelectedIndex() != -1) {
                        int index = listConnected.getSelectedIndex();
                        String username = listConnected.getModel().getElementAt(index);

                        ListModel<String> recipientsModel = listRecipients.getModel();
                        ArrayList<String> recipientsStrings = new ArrayList<String>();

                        for (int i = 0; i < recipientsModel.getSize(); i++) {
                            recipientsStrings.add(recipientsModel.getElementAt(i));
                        }

                        if(recipientsStrings.contains(username)) {
                            JOptionPane.showMessageDialog(null,"User is already in the list of recipients", "ERROR", JOptionPane.ERROR_MESSAGE);
                        } else {
                            recipientsStrings.add(username);
                            DefaultListModel<String> newModel = new DefaultListModel<String>();

                            for(String s : recipientsStrings) {
                                newModel.addElement(s);
                            }
                            listRecipients.setModel(newModel);
                        }
                    }
                    if(listContacts.getSelectedIndex() != -1) {
                        int index = listContacts.getSelectedIndex();
                        String username = listContacts.getModel().getElementAt(index);

                        ListModel<String> recipientsModel = listRecipients.getModel();
                        ArrayList<String> recipientsStrings = new ArrayList<String>();

                        for (int i = 0; i < recipientsModel.getSize(); i++) {
                            recipientsStrings.add(recipientsModel.getElementAt(i));
                        }

                        if(recipientsStrings.contains(username)) {
                            JOptionPane.showMessageDialog(null,"User is already in the list of recipients", "ERROR", JOptionPane.ERROR_MESSAGE);
                        } else {
                            recipientsStrings.add(username);
                            DefaultListModel<String> newModel = new DefaultListModel<String>();

                            for(String s : recipientsStrings) {
                                newModel.addElement(s);
                            }
                            listRecipients.setModel(newModel);
                        }
                    }
                }
            }

            if(e.getSource() == btnRemove) {
                if(listRecipients.getSelectedIndex() != -1) {
                    int index = listRecipients.getSelectedIndex();

                    ListModel<String> recipientsModel = listRecipients.getModel();
                    DefaultListModel<String> newModel = new DefaultListModel<String>();

                    for (int i = 0; i < recipientsModel.getSize(); i++) {
                        if(i != index) {
                            newModel.addElement(recipientsModel.getElementAt(i));
                        }
                    }
                    listRecipients.setModel(newModel);
                } else {
                    JOptionPane.showMessageDialog(null,"Selection must be in the list of recipients", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class ListListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(e.getSource() == listConnected) {
                listContacts.clearSelection();
                listRecipients.clearSelection();
            }

            if(e.getSource() == listContacts) {
                listConnected.clearSelection();
                listRecipients.clearSelection();
            }

            if(e.getSource() == listRecipients) {
                listConnected.clearSelection();
                listContacts.clearSelection();
            }
        }
    }

}
