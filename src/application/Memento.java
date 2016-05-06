package application;

import java.util.ArrayList;

/**
 * Created by dmitry on 03.05.16.
 */
public class Memento {

    private ArrayList<Vertex> vertexes;
    private ArrayList<Edge> edges;
    private ArrayList<String> historyLog;

    public Memento(ArrayList<Vertex> vertexes, ArrayList<Edge> edges, ArrayList<String> historyLog) {
        this.vertexes = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.historyLog = new ArrayList<>();
        for (String str : historyLog) {
            this.historyLog.add(str);
        }
        for (Vertex current : vertexes) {
            this.vertexes.add(current.clone());
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
            newEdge.setWeight((Integer) edge.getWeight().getValue());
            this.edges.add(newEdge);
        }
    }

    public ArrayList<Vertex> getVertexes() {
        ArrayList<Vertex> newVer = new ArrayList<>();
        for (Vertex current : vertexes) {
            newVer.add(current.clone());
        }
        return newVer;
    }

    public ArrayList<Edge> getEdges() {
        ArrayList<Edge> newEdges = new ArrayList<>();
        for (Edge current : edges) {
            newEdges.add(current.clone());
        }
        return newEdges;
    }

    public ArrayList<String> getHistoryLog() {
        return historyLog;
    }
}
