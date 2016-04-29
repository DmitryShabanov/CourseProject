package UserInterface;

/**
 * Created by dmitry on 28.04.16.
 */
public class Edge {

    private Vertex start, end;

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

    public boolean equals(Edge edge) {
        return edge.getStart().equals(start) && edge.getEnd().equals(end) || edge.getStart().equals(end) && edge.getEnd().equals(start);
    }
}
