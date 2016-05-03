package application;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by dmitry on 09.04.16.
 */
public class DragListener extends MouseInputAdapter {

    private Point location;
    private MouseEvent pressed;
    private JPanel dropPanel;

    public DragListener(JPanel dropPanel) {
        this.dropPanel = dropPanel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = e;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Component component = e.getComponent();
        location = component.getLocation(location);
        int x = location.x - pressed.getX() + e.getX();
        int y = location.y - pressed.getY() + e.getY();
        if (x + component.getWidth() > dropPanel.getWidth() + dropPanel.getX() || x < 0) {
            return;
        }
        if (y + component.getHeight() > dropPanel.getHeight() || y < 0) {
            return;
        }
        component.setLocation(x, y);
        dropPanel.repaint();
    }
}