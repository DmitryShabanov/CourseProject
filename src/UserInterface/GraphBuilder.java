package UserInterface;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
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

    private JFormattedTextField start = new JFormattedTextField(0);
    private JFormattedTextField finish = new JFormattedTextField(0);
    private JTextField resultField = new JTextField();

    public GraphBuilder() throws HeadlessException {
        super("Graph Builder");
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);

        addMenu();
        addToolBar();
        add(workPanel);

        setVisible(true);
    }

    private void addToolBar() {
        JToolBar toolBar = new JToolBar();
        JButton addVer = new JButton("Вершина");
        JButton calculateWay = new JButton("Путь");

        addVer.addActionListener(new AddVertexListener());
        calculateWay.addActionListener(new WayListener());
        start.setColumns(2);
        finish.setColumns(2);

        toolBar.add(addVer);
        toolBar.add(calculateWay);
        toolBar.add(new JLabel(" Начальная вершина:  "));

        toolBar.add(start);
        start.setMaximumSize(new Dimension(30, 20));
        toolBar.add(new JLabel(" Конечная вершина:  "));
        toolBar.add(finish);
        finish.setMaximumSize(new Dimension(30, 20));
        toolBar.add(new JLabel(" Вес кратчайшего пути: "));
        toolBar.add(resultField);
        resultField.setMaximumSize(new Dimension(140, 20));
        resultField.setEditable(false);

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

    public class AddVertexListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Vertex current : vertexes) {
                current.resetIcon();
            }
            saveState();
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
            JLabel component = (JLabel) e.getComponent();
            for (Vertex current : vertexes) {
                current.resetIcon();
            }
            if (!edgeFlag) {
                edge = new Edge();
                for (Vertex current : vertexes) {
                    if (current.getNumber().compareTo(component.getText()) == 0) {
                        edge.setStart(current);
                    }
                }
                edgeFlag = true;
                component.setIcon(new ImageIcon("selectedVer.png"));
                workPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        edgeFlag = false;
                        edge.getStart().resetIcon();
                    }
                });
            } else {
                for (Vertex current : vertexes) {
                    if (current.getNumber().compareTo(component.getText()) == 0) {
                        if (component.getX() == edge.getStart().getLocation().getX() && component.getY() == edge.getStart().getLocation().getY()) {
                            return;
                        }
                        edge.setEnd(current);
                    }
                }
                edgeFlag = false;
                edge.getStart().resetIcon();
                workPanel.addEdge(edge);
                saveState();
                workPanel.validate();
                rePaint();
            }
        }
    }

    public class WayListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Vertex vertex : vertexes) {
                vertex.resetIcon();
            }
            validWay(start);
            validWay(finish);
            if (vertexes.size() == 0) {
                return;
            }
            if ((int) start.getValue() == (int) finish.getValue()) {
                resultField.setText("не существует!");
                return;
            }
            ArrayList<Integer> result = workPanel.getShortestWay(vertexes.size(), (int) start.getValue(), (int) finish.getValue(), resultField);
            for (Integer current : result) {
                vertexes.get(current).changeIcon();
            }
        }
    }

    private void validWay(JFormattedTextField tf) {
        if (tf.getValue() == null) {
            tf.setValue(0);
        }
        if ((Integer) tf.getValue() < 0) {
            tf.setValue(Math.abs((Integer) tf.getValue()));
        }
        if ((Integer) tf.getValue() > vertexes.size() - 1) {
            tf.setValue(0);
        }
    }

    private void rePaint() {
        int counter = 0;
        for (Point current : points) {
            vertexes.get(counter).setLocation(current);
            counter++;
        }
    }

    private void saveState() {
        points = new ArrayList<>();
        if (vertexes.size() > 0) {
            for (Vertex current : vertexes) {
                points.add(current.getLocation());
            }
        }
    }
}
