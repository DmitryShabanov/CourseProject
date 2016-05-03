package application;

import java.util.ArrayList;

/**
 * Created by dmitry on 03.05.16.
 */
public class Memento {

    private ArrayList<Vertex> vertexes;
    private ArrayList<Edge> edges;

    public Memento(ArrayList<Vertex> vertexes, ArrayList<Edge> edges) {
        this.vertexes = new ArrayList<>();
        this.edges = new ArrayList<>();
        for (Vertex current : vertexes) {
            this.vertexes.add(current.copy());
        }
        for (Edge edge : edges) {
            Edge newEdge = new Edge();
            for (Vertex vertex: this.vertexes) {
                if (vertex.getNumber().compareTo(edge.getStart().getNumber()) == 0) {
                    newEdge.setStart(vertex);
                }
                if (vertex.getNumber().compareTo(edge.getEnd().getNumber()) == 0) {
                    newEdge.setEnd(vertex);
                }
            }
            newEdge.setWeight(edge.getWeight());
            this.edges.add(newEdge);
        }
    }

    public ArrayList<Vertex> getVertexes() {
        ArrayList<Vertex> newVer = new ArrayList<>();
        for (Vertex current : vertexes) {
            newVer.add(current.copy());
        }
        return newVer;
    }

    public ArrayList<Edge> getEdges() {
        ArrayList<Edge> newEdges = new ArrayList<>();
        for (Edge current : edges) {
            newEdges.add(current.copy());
        }
        return newEdges;
    }
}
