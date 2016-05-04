package application;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dmitry on 15.04.16.
 */
public class Vertex {

    private JLabel icon;
    private JPanel panel;
    private String number = "";
    private boolean selected = false;

    public Vertex(JPanel panel) {
        icon = new JLabel(new ImageIcon("ver.png"));
        this.panel = panel;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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

    public void resetIcon() {
        icon.setIcon(new ImageIcon("ver.png"));
    }

    public void changeIcon() {
        icon.setIcon(new ImageIcon("suitedVer.png"));
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

    public void setIcon(JLabel icon) {
        this.icon = icon;
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

    public Vertex copy() {
        Vertex clone = new Vertex(panel);
        clone.setNumber(Integer.valueOf(number));
        clone.setLocation(getLocation());
        return clone;
    }
}
