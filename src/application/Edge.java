package application;

import javax.swing.*;

/**
 * Created by dmitry on 28.04.16.
 */
public class Edge implements Cloneable {

    private Vertex start, end;
    private JFormattedTextField weight;

    public Edge() {
        weight = new JFormattedTextField(1);
        weight.setColumns(2);
    }

    public void validWeight() {
        if (weight.getValue() == null) {
            weight.setValue(1);
        }
        if ((Integer) weight.getValue() < 0) {
            weight.setValue(Math.abs((Integer) weight.getValue()));
        }
        if ((Integer) weight.getValue() == 0) {
            weight.setValue(1);
        }
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

    public void setWeight(JFormattedTextField weight) {
        this.weight = weight;
    }

    public void setWeight(int weight) {
        this.weight.setValue(weight);
    }

    public JFormattedTextField getWeight() {
        return weight;
    }

    public boolean equals(Edge edge) {
        return edge.getStart().equals(start) && edge.getEnd().equals(end);
    }

    public boolean counterEdge(Edge edge) {
        return edge.getEnd().equals(start) && edge.getStart().equals(end);
    }

    public Edge clone() {
        Edge clone = new Edge();
        clone.setStart(start.clone());
        clone.setEnd(end.clone());
        clone.setWeight((Integer) weight.getValue());
        return clone;
    }
}
