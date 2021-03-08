package model;

import javax.swing.*;
import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private ImageIcon image;

    public User (String username) {
        this.username = username;
    }
    public User(String username, ImageIcon image) {
        this.username = username;
        this.image = image;
    }

    public int hashCode() {
        return username.hashCode();
    }

    public boolean equals(Object obj) {
        if(obj!=null && obj instanceof User)
            return username.equals(((User)obj).getUserName());
        return false;
    }

    public String getUserName() {
        return username;
    }

    public ImageIcon getImage() {
        return image;
    }
}
