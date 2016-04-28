package UserInterface;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dmitry on 15.04.16.
 */
public class Vertex implements Cloneable {

    private JLabel icon;
    private JPanel panel;
    private String number = "";

    public Vertex(JPanel panel) {
        icon = new JLabel(new ImageIcon("src/UserInterface/vertex.png"));
        this.panel = panel;
    }

    public Point getLocation() {
        return new Point(icon.getX(), icon.getY());
    }

    public void setLocation(Point location) {
        icon.setLocation(location);
    }

    public void draw() {
        DragListener drag = new DragListener(panel);
        icon.addMouseListener(drag);
        icon.addMouseMotionListener(drag);
        panel.add(icon);
        panel.validate();
    }

    public int getHeight() {
        return icon.getHeight();
    }

    public int getWidth() {
        return icon.getWidth();
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

    @Override
    public String toString() {
        return number;
    }

    @Override
    protected Vertex clone() {
        return new Vertex(panel);
    }
}
