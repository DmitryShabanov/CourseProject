import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by dmitry on 09.04.16.
 */
public class DragListener extends MouseInputAdapter {
    private Point location;
    private MouseEvent pressed;

    public DragListener() {
    }

    public void mousePressed(MouseEvent e) {
        pressed = e;
    }

    public void mouseDragged(MouseEvent e) {
        Component component = e.getComponent();
        location = component.getLocation(location);
        int x = location.x - pressed.getX() + e.getX();
        int y = location.y - pressed.getY() + e.getY();
        component.setLocation(x, y);
    }
}