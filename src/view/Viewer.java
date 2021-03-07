package view;

import javax.swing.*;
import java.awt.*;

public abstract class Viewer extends JFrame implements IViewer {

    public Viewer(String title, int width, int height) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(setContent());
        setPreferredSize(new Dimension(width, height));
        setSize(width, height);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public void location(int x, int y) {
        setLocation(x,y);
    }

    public void display() {
        setVisible(true);
    }
}
