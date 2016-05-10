package application;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dmitry on 15.04.16.
 */
public class Vertex implements Cloneable, MyComponent {

    private JLabel icon;
    private JPanel panel;
    private String number = "";

    public Vertex(JPanel panel) {
        icon = new JLabel(new ImageIcon("images/ver.png"));
        this.panel = panel;
    }

    public Point getLocation() {
        return new Point(icon.getLocation());
    }

    public void setLocation(Point location) {
        icon.setLocation(location);
    }

    public void draw() {
        panel.add(icon);
        panel.validate();
    }

    public String getNumber() {
        return number;
    }

    public int getCenterX() {
        return (icon.getWidth() / 2) + icon.getX();
    }

    public int getCenterY() {
        return (icon.getHeight() / 2) + icon.getY();
    }

    public JLabel getIcon() {
        return icon;
    }

    public void setNumber(int number) {
        this.number = number + "";
        icon.setText(this.number);
        icon.setVerticalTextPosition(SwingConstants.CENTER);
        icon.setHorizontalTextPosition(SwingConstants.CENTER);
    }

    public boolean equals(Vertex vertex) {
        return vertex.getCenterX() == getCenterX() && vertex.getCenterY() == getCenterY() && vertex.getNumber().compareTo(number) == 0;
    }

    public Vertex clone() {
        Vertex clone = new Vertex(panel);
        clone.setNumber(Integer.valueOf(number));
        clone.setLocation(getLocation());
        return clone;
    }
}
