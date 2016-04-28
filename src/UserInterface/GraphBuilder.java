package UserInterface;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by dmitry on 27.04.16.
 */
public class GraphBuilder extends JFrame {

    private DropPanel workPanel = new DropPanel();
    private Vertex vertex = new Vertex(workPanel);
    private ArrayList<Vertex> vertexes = new ArrayList<>();
    private ArrayList<Point> points = new ArrayList<>();

    private Edge edge;
    private boolean edgeFlag = false;

    public GraphBuilder() throws HeadlessException {
        super("Graph Builder");
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 550);
        setLocationRelativeTo(null);

        addMenu();
        addToolBar();
        add(workPanel);

       /* Edge e = new Edge();
        e.setStart(new Point(30, 30));
        e.setEnd(new Point(180, 190));
        workPanel.addEdge(e);

        Edge e1 = new Edge();
        e1.setStart(new Point(320, 10));
        e1.setEnd(new Point(180, 190));
        workPanel.addEdge(e1);*/

        setVisible(true);
    }

    public void rePaint() {
        int counter = 0;
        for (Point current : points) {
            vertexes.get(counter).setLocation(current);
            counter++;
        }
    }

    public class AddVertexListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            points = new ArrayList<>();
            if (vertexes.size() > 0) {
                for (Vertex current : vertexes) {
                    points.add(current.getLocation());
                }
            }
            Vertex newVer = vertex.clone();
            newVer.setNumber(vertexes.size());
            newVer.draw();

            AddEdgeListener drag = new AddEdgeListener();
            newVer.getIcon().addMouseListener(drag);
            newVer.getIcon().addMouseMotionListener(drag);

            vertexes.add(newVer);
            rePaint();
        }
    }

    public class AddEdgeListener extends MouseInputAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = (int) (e.getComponent().getLocation().getX() + (e.getComponent().getWidth() / 2));
            int y = (int) (e.getComponent().getLocation().getY() + (e.getComponent().getHeight() / 2));
            if (!edgeFlag) {
                edge = new Edge();
                edge.setStart(new Point(x, y));
                edgeFlag = true;
            } else {
                edge.setEnd(new Point(x, y));
                edgeFlag = false;
                workPanel.addEdge(edge);
            }
        }
    }

    private void addToolBar() {
        JToolBar toolBar = new JToolBar();
        JButton vertex = new JButton("Vertex");
        AddVertexListener l = new AddVertexListener();

        toolBar.add(vertex);
        vertex.addActionListener(l);

        this.add(toolBar, BorderLayout.NORTH);
    }

    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Меню");
        JMenuItem menuItem = new JMenuItem("Добавить вершину");

        menu.add(menuItem);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
    }
}
