package UI;

import controller.client.ClientController;
import model.Message;
import model.User;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
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
  private JFileChooser fileChooser = new JFileChooser();
  private JTextArea inputWindow = new JTextArea("Write a message...");
  private JButton sendButton = new JButton("send");
  private JButton logoutButton = new JButton("log out");
  private JButton addFileButton = new JButton("Add a photo");
  private JButton addReceiverButton = new JButton("Add a receiver");

  private JList messageWindow = new JList();
  private JList contactWindow = new JList();
  private JScrollPane scrollPane = new JScrollPane(messageWindow);
  private MRecipientsFrame recipientsFrame;

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
    JFrame frame = new JFrame();
        frame.setTitle("Chat console");
        frame.setBounds(100,100,820,600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(this);

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
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    eastPanel.add(inputWindow, BorderLayout.SOUTH);
    inputWindow.setVisible(true);
    inputWindow.setRows(3);

    JPanel westPanel = new JPanel(new BorderLayout());
    westPanel.setPreferredSize(new Dimension(150, 150));
    westPanel.setLayout(new BorderLayout());
    westPanel.setVisible(true);
    westPanel.add(contactWindow, BorderLayout.CENTER);

    eastPanel.add(addFileButton, BorderLayout.EAST);
    eastPanel.add(addReceiverButton, BorderLayout.NORTH);

    contactWindow.setVisible(true);
    contactWindow.setBackground(new Color(0x438F99));

    eastPanel.add(westPanel, BorderLayout.WEST);
    westPanel.add(sendButton, BorderLayout.SOUTH);
    westPanel.add(logoutButton, BorderLayout.PAGE_START);

    ButtonListener listener = new ButtonListener();
    sendButton.addActionListener(listener);
    logoutButton.addActionListener(listener);
    addFileButton.addActionListener(listener);
    addReceiverButton.addActionListener(listener);

    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG & GIF Images", "jpg", "gif");
    fileChooser.setFileFilter(filter);

    messageWindow.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
          inputWindow.setText("");
          inputWindow.setEditable(true);
          inputWindow.requestFocus();
        }
      }
    });
  }

  private class ButtonListener implements ActionListener {

    String fileName;

    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == sendButton) { //ska skicka text+bildtext+lista med receivers till controller.
        String text = inputWindow.getText();
        //String[] receivers = getSelectedContacts();
        if (fileName != null) {
          //controller.sendMessage(text, fileName, receivers);
        }
        else {
          //controller.sendMessage(text, receivers);
          //
        }

      } else if (e.getSource() == logoutButton) {
        JOptionPane.showMessageDialog(null, "You are now logged out");
        controller.disconnectClient();
      } else if (e.getSource() == addFileButton) {
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
          fileName = fileChooser.getSelectedFile().getPath();
          JOptionPane.showMessageDialog(null, "Image successfully uploaded");
        }
      }
      else if (e.getSource() == addReceiverButton) {
        recipientsFrame = new MRecipientsFrame(controller);
        //starta en frame som visar kontakter + onlinekontakter
      }
    }
  }

    public void propertyChange(PropertyChangeEvent evt) { //uppdaterar meddelanden.
      if (evt.getPropertyName().equals("message")) {
        Message message = (Message) evt.getNewValue();
        updateMessageWindow(message);
      }
      else if (evt.getPropertyName().equals("ConnectedUsers")) {
        controller.updateConnectedList((User[]) evt.getNewValue());

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
