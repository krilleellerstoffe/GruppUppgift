package client;

import model.Message;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ClientConsole extends JPanel implements PropertyChangeListener {

  private ClientController controller;
  private Message[] messageList = new Message[100];
  //private Contact[] contactList = new Contact[100];
  private JFileChooser fileChooser = new JFileChooser();
  private JTextArea inputWindow = new JTextArea("Write a message...");
  private JButton sendButton = new JButton("send");
  private JButton logoutButton = new JButton("log out");
  private JButton addFileButton = new JButton("Add a photo");
  private JList messageWindow = new JList();
  private JList contactWindow = new JList();
  private JScrollPane scrollPane = new JScrollPane(messageWindow);

  public ClientConsole(ClientController client) {
    this.controller = client;
    Border border = this.getBorder();
    Border margin = BorderFactory.createLineBorder(Color.BLACK, 1);
    setBorder(new CompoundBorder(border, margin));
    setSize(800, 1000);
    setLayout(new BorderLayout());
    setVisible(true);
    setupWestPanel();
  }

  private void setupWestPanel() {
    JPanel eastPanel = new JPanel(new BorderLayout());
    add(eastPanel, BorderLayout.EAST);
    eastPanel.setPreferredSize(new Dimension(800, 1000));
    eastPanel.setLayout(new BorderLayout());
    Border border = eastPanel.getBorder();
    Border margin = BorderFactory.createLineBorder(Color.BLACK, 1);
    setBorder(new CompoundBorder(border, margin));
    setVisible(true);

    eastPanel.add(messageWindow, BorderLayout.CENTER);
    messageWindow.setVisible(true);
    messageWindow.setBackground(new Color(0xECECF3));
    messageWindow.setPreferredSize(new Dimension(100, 100));
    messageWindow.add(scrollPane);
    scrollPane.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    //eastPanel.add(contactWindow, BorderLayout.WEST);

    eastPanel.add(inputWindow, BorderLayout.SOUTH);
    inputWindow.setVisible(true);
    inputWindow.setRows(3);

    JPanel buttonPanel = new JPanel(new BorderLayout());
    buttonPanel.setPreferredSize(new Dimension(150, 150));
    buttonPanel.setLayout(new BorderLayout());
    buttonPanel.setVisible(true);
    buttonPanel.add(contactWindow, BorderLayout.CENTER);
    buttonPanel.add(addFileButton, BorderLayout.PAGE_END);
    contactWindow.setVisible(true);
    contactWindow.setBackground(new Color(0x438F99));

    eastPanel.add(buttonPanel, BorderLayout.WEST);
    buttonPanel.add(sendButton, BorderLayout.SOUTH);
    buttonPanel.add(logoutButton, BorderLayout.PAGE_START);

    messageWindow.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            inputWindow.setText("");
            inputWindow.setEditable(true);
            inputWindow.requestFocus();
        }
      }
    });

    updateContacts();
  }

  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == sendButton) {
        String text = inputWindow.getText();
        //hämta även receiver från contactWindow
      } else if (e.getSource() == logoutButton) { //fungerar ej
        JOptionPane.showMessageDialog(null, "You are now logged out");
        controller.disconnectClient();
      }
    }
  }

  public void propertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals("message")) {
      Message message = (Message) evt.getNewValue();
      updateMessageWindow(message);
    } else if (evt.getPropertyName().equals("contact")) {
      //hämta klient
    }
  }

  private void updateMessageWindow(Message message) {
    for (int i = 0; i < messageList.length; i++) {
      if (messageList[i] == null) {
        messageList[i] = message;
      }
      messageWindow.setListData(messageList);
    }
  }

  private void updateContacts() {
    //contactList = controller.getContacts();
    //contactWindow.setListData(contactList);
  }
}