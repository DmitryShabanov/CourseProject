package UserInterface;

import javax.swing.*;

/**
 * Created by dmitry on 28.04.16.
 */
public class Edge {

    private Vertex start, end;
    private JTextField weight;

    public Edge() {
        weight = new JTextField("1", 2);
    }

    public void drawWeight() {
        int x = (start.getCenterX() + end.getCenterX()) / 2;
        int y = (start.getCenterY() + end.getCenterY()) / 2;
        weight.setLocation(x, y);
    }

    public Vertex getStart() {
        return start;
    }

    public void setStart(Vertex start) {
        this.start = start;
    }

    public Vertex getEnd() {
        return end;
    }

    public void setEnd(Vertex end) {
        this.end = end;
    }

    public void setWeight(String weight) {
        this.weight.setText(weight);
    }

    public JTextField getWeight() {
        return weight;
    }

    public boolean equals(Edge edge) {
        return edge.getStart().equals(start) && edge.getEnd().equals(end) || edge.getStart().equals(end) && edge.getEnd().equals(start);
    }
}
