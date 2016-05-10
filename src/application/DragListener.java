package application;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by dmitry on 07.05.16.
 */
public class DragListener extends MouseInputAdapter {
    private Point location;
    private MouseEvent pressed;
    private int oldX = 0;
    private int oldY = 0;

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = e;
        oldX = e.getComponent().getX();
        oldY = e.getComponent().getY();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel label = (JLabel) e.getComponent();
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (GraphBuilder.getInstance().select.compareTo(label.getText()) == 0) {
            return;
        }
        for (Vertex current : GraphBuilder.getInstance().vertexes) {
            if (current.getNumber().compareTo(GraphBuilder.getInstance().select) != 0) {
                MyComponent decorator = new ResetDecorator(current);
                decorator.draw();
            }
        }
        for (Vertex vertex : GraphBuilder.getInstance().vertexes) {
            if (vertex.getNumber().compareTo(label.getText()) == 0) {
                MyComponent decorator = new HoverDecorator(vertex);
                decorator.draw();
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JLabel label = (JLabel) e.getComponent();
        if (GraphBuilder.getInstance().select.compareTo(label.getText()) == 0) {
            return;
        }
        for (Vertex vertex : GraphBuilder.getInstance().vertexes) {
            if (vertex.getNumber().compareTo(label.getText()) == 0) {
                MyComponent decorator = new ResetDecorator(vertex);
                decorator.draw();
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Component component = e.getComponent();
        location = component.getLocation(location);
        int x = location.x - pressed.getX() + e.getX();
        int y = location.y - pressed.getY() + e.getY();
        if (x + component.getWidth() > GraphBuilder.getInstance().workPanel.getWidth() + GraphBuilder.getInstance().workPanel.getX() || x < 0) {
            return;
        }
        if (y + component.getHeight() > GraphBuilder.getInstance().workPanel.getHeight() || y < 0) {
            return;
        }
        component.setLocation(x, y);
        GraphBuilder.getInstance().workPanel.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (oldX != e.getComponent().getX() && oldY != e.getComponent().getY()) {
            GraphBuilder.getInstance().saveState();
            GraphBuilder.getInstance().saveVertexLocation();
        }
    }
}