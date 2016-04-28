package UserInterface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by dmitry on 26.04.16.
 */
public class DropPanel extends JPanel {

    private ArrayList<Edge> edges = new ArrayList<>();

    public void addEdge(Edge edge) {
        edges.add(edge);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Edge edge : edges) {
            graphics2D.drawLine((int) edge.getStart().getX(), (int) edge.getStart().getY(), (int) edge.getEnd().getX(), (int) edge.getEnd().getY());
        }
    }
}
