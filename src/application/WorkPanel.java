package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by dmitry on 26.04.16.
 */
public class WorkPanel extends JPanel {

    private ArrayList<Edge> edges = new ArrayList<>();

    public boolean addEdge(Edge edge) {
        for (Edge current : edges) {
            if (current.equals(edge)) {
                return false;
            }
            if (current.counterEdge(edge)) {
                edge.setWeight(current.getWeight());
                edges.add(edge);
                repaint();
                return false;
            }
        }
        edge.getWeight().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_X) {
                    Component component = e.getComponent();
                    for (int i = 0; i < edges.size(); i++) {
                        if (edges.get(i).getWeight().equals(component)) {
                            GraphBuilder.getInstance().historyModel.add(0, "Удалено ребро: от вершины " + edges.get(i).getStart().getNumber() + " к вершине " + edges.get(i).getEnd().getNumber());
                            GraphBuilder.getInstance().saveVertexLocation();
                            remove(edges.get(i).getWeight());
                            validate();
                            repaint();
                            GraphBuilder.getInstance().rePaint();
                            edges.remove(edges.get(i));
                            i--;
                        }
                    }
                    GraphBuilder.getInstance().saveState();
                    GraphBuilder.getInstance().setFocus();
                }
            }
        });
        edges.add(edge);
        repaint();
        add(edge.getWeight());
        return true;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Edge edge : edges) {
            int x1 = edge.getStart().getCenterX();
            int y1 = edge.getStart().getCenterY();
            int x2 = edge.getEnd().getCenterX();
            int y2 = edge.getEnd().getCenterY();
            drawArrowLine(g, x1, y1, x2, y2);
            edge.drawWeight();
        }
    }

    private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2) {
        int dx = x2 - x1, dy = y2 - y1, d = 22, h = 4;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;
        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;
        int[] xPoints = {x2, (int) xm, (int) xn};
        int[] yPoints = {y2, (int) ym, (int) yn};

        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xPoints, yPoints, 3);
    }

    public ArrayList<Integer> getShortestWay(int size, int start, int finish, JTextField resField) {
        int[][] vertexArray = new int[size][size];
        for (int i = 0; i < vertexArray.length; i++) {
            for (int j = 0; j < vertexArray[i].length; j++) {
                vertexArray[i][j] = 0;
            }
        }

        for (Edge current : edges) {
            int i = Integer.valueOf(current.getStart().getNumber());
            int j = Integer.valueOf(current.getEnd().getNumber());
            current.validWeight();
            vertexArray[i][j] = (int) current.getWeight().getValue();
        }

        Graph graph = new Graph(vertexArray);

        graph.floyd();
        int weight = graph.printWay(start, finish);
        if (weight < 0) {
            resField.setText("не существует!");
        } else {
            resField.setText("" + weight);
        }

        return graph.getResult();
    }
}
