package UserInterface;

import javax.swing.*;

/**
 * Created by dmitry on 15.04.16.
 */
public class Vertex implements Cloneable {

    private JLabel icon;
    private JPanel panel;
    private String number = "";

    public Vertex(JPanel panel) {
        icon = new JLabel(new ImageIcon("/media/Data/Java/CourseProject/src/UserInterface/vertex.png"));
        this.panel = panel;
    }

    public void draw() {
        DragListener drag = new DragListener(panel);
        icon.addMouseListener(drag);
        icon.addMouseMotionListener(drag);
        panel.add(icon);
        panel.validate();
    }

    public void setNumber(int number) {
        this.number = number + "";
        icon.setText(this.number);
        icon.setVerticalTextPosition(SwingConstants.CENTER);
        icon.setHorizontalTextPosition(SwingConstants.CENTER);

    }

    @Override
    protected Vertex clone() {
        return new Vertex(panel);
    }
}
