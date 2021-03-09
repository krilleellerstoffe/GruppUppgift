package view.server;

import javax.swing.*;
import java.awt.*;

/**
 * En abstrakt superklass som skapar view fönster med olika innehåll.
 * @version 1.0
 * @author Viktor Johansson
 */
public abstract class Viewer extends JFrame implements IViewer {

    public BorderLayout layout;

    /**
     * Konstruerar ett fönster med angiven information, såsom titel, bredd och höjd.
     * @param title namn till fönstret
     * @param width bredd
     * @param height höjd
     */
    public Viewer(String title, int width, int height) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(width, height));
        setSize(width, height);
        setResizable(false);
        setLocationRelativeTo(null);
        layout = new BorderLayout();
        setLayout(layout);
    }

    /**
     * Används om man vill att fönstret ska hamna på specifik plats på skärmen.
     * @param x x-axis
     * @param y y-axis
     */
    public void location(int x, int y) {
        setLocation(x,y);
    }
}
