package application;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by dmitry on 27.04.16.
 */
public class GraphBuilder extends JFrame {

    private WorkPanel workPanel = new WorkPanel();
    private ArrayList<Vertex> vertexes = new ArrayList<>();
    private ArrayList<Point> points = new ArrayList<>();

    private Edge edge;
    private boolean edgeFlag = false;

    private JFormattedTextField start = new JFormattedTextField(0);
    private JFormattedTextField finish = new JFormattedTextField(0);
    private JTextField resultField = new JTextField();

    private DefaultListModel historyModel = new DefaultListModel();
    private JList historyList = new JList(historyModel);
    private JScrollPane historyScrollPane = new JScrollPane(historyList);
    private History history = new History();
    private int currentState = -1;

    private String select = "";
    private ArrayList<String> numbers = new ArrayList<>();

    public GraphBuilder() throws HeadlessException {
        super("Graph Builder");
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1350, 800);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);

        addToolBar();
        add(workPanel);
        addHistory();

        saveState();

        workPanel.addKeyListener(new MyKeyListener());
        setFocus();

        setVisible(true);
    }

    private void setFocus() {
        workPanel.setFocusable(true);
        workPanel.requestFocusInWindow();
    }

    private void saveVertexLocation() {
        points = new ArrayList<>();
        if (vertexes.size() > 0) {
            for (Vertex current : vertexes) {
                points.add(current.getLocation());
            }
        }
    }

    private void saveState() {
        ArrayList<String> historyLog = new ArrayList<>();
        for (int i = 0; i < historyModel.size(); i++) {
            historyLog.add((String) historyModel.get(i));
        }
        Memento state = new Memento(vertexes, workPanel.getEdges(), historyLog);
        currentState++;
        history.addState(state, currentState);
    }

    private boolean loadState(int index) {
        clearState();
        historyModel.removeAllElements();
        Memento state = history.getState(index);
        if (state == null) {
            return false;
        }
        for (String log : state.getHistoryLog()) {
            historyModel.addElement(log);
        }
        vertexes = state.getVertexes();
        for (Vertex current : vertexes) {
            addVertex(current);
        }
        ArrayList<Edge> edges = state.getEdges();
        for (Edge edge : edges) {
            Edge newEdge = new Edge();
            for (Vertex vertex : this.vertexes) {
                if (vertex.getNumber().compareTo(edge.getStart().getNumber()) == 0) {
                    newEdge.setStart(vertex);
                }
                if (vertex.getNumber().compareTo(edge.getEnd().getNumber()) == 0) {
                    newEdge.setEnd(vertex);
                }
            }
            newEdge.setWeight(edge.getWeight());
            workPanel.addEdge(newEdge);
        }
        saveVertexLocation();
        workPanel.validate();
        rePaint();
        return true;
    }

    private void clearState() {
        points.clear();
        vertexes.clear();
        resultField.setText("");
        workPanel.getEdges().clear();
        workPanel.removeAll();
        workPanel.validate();
        workPanel.repaint();
        historyModel.removeAllElements();
        historyModel.add(0, "Рабочее пространство очищено");
    }

    private void rePaint() {
        int counter = 0;
        for (Point current : points) {
            vertexes.get(counter).setLocation(current);
            counter++;
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

    private void addToolBar() {
        JToolBar toolBar = new JToolBar();
        JButton addVer = new JButton("Добавить вершину");
        JButton calculateWay = new JButton("Найти путь");

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
        JButton clear = new JButton("Очистить");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearState();
                saveState();
                setFocus();
            }
        });
        toolBar.add(clear);

        JButton b1 = new JButton(new ImageIcon("forwardButton.png"));
        JButton b2 = new JButton(new ImageIcon("backButton.png"));
        b1.setMaximumSize(new Dimension(30, 30));
        b2.setMaximumSize(new Dimension(30, 30));
        toolBar.add(b2);
        toolBar.add(b1);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ctrlY();
                setFocus();
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ctrlZ();
                setFocus();
            }
        });

        this.add(toolBar, BorderLayout.NORTH);
    }

    private void ctrlY() {
        if (loadState(currentState + 1)) {
            currentState++;
        }
        loadState(currentState);
    }

    private void ctrlZ() {
        if (loadState(currentState - 1)) {
            currentState--;
        }
        loadState(currentState);
    }

    private void addHistory() {
        historyScrollPane.setPreferredSize(new Dimension(this.getWidth(), 100));
        this.add(historyScrollPane, BorderLayout.SOUTH);
        historyList.setLayoutOrientation(JList.VERTICAL);
    }

    private void addVertex(Vertex vertex) {
        AddEdgeListener edgeListener = new AddEdgeListener();
        vertex.getIcon().addMouseListener(edgeListener);
        vertex.getIcon().addMouseMotionListener(edgeListener);
        saveVertexLocation();
        vertex.draw();
        DragListener drag = new DragListener();
        vertex.getIcon().addMouseListener(drag);
        vertex.getIcon().addMouseMotionListener(drag);
        rePaint();
    }

    private void addNewVer() {
        for (Vertex current : vertexes) {
            current.resetIcon();
        }
        Vertex newVer = new Vertex(workPanel);
        if (!numbers.isEmpty()) {
            sortNumbers();
            newVer.setNumber(Integer.valueOf(numbers.get(0)));
            numbers.remove(0);
        } else {
            newVer.setNumber(vertexes.size());
        }
        addVertex(newVer);
        vertexes.add(newVer);
        historyModel.add(0, "Добавлена новая вершина: " + newVer.getNumber());
        saveState();
    }

    private void sortNumbers() {
        boolean isSwapped = true;
        while (isSwapped) {
            isSwapped = false;
            for (int j = 1; j < numbers.size(); j++) {
                if (Integer.valueOf(numbers.get(j - 1)) > Integer.valueOf(numbers.get(j))) {
                    numbers.add(j - 1, numbers.get(j));
                    numbers.remove(j + 1);
                    isSwapped = true;
                }
            }
        }
    }

    private void deleteVertex() {
        for (Vertex vertex : vertexes) {
            if (vertex.getNumber().compareTo(select) == 0) {
                boolean isDelete = true;
                while (isDelete) {
                    isDelete = false;
                    for (Edge edge : workPanel.getEdges()) {
                        if (edge.getStart().equals(vertex) || edge.getEnd().equals(vertex)) {
                            workPanel.remove(edge.getWeight());
                            workPanel.getEdges().remove(edge);
                            isDelete = true;
                            break;
                        }
                    }
                }
                workPanel.remove(vertex.getIcon());
                numbers.add(vertex.getNumber());
                vertexes.remove(vertex);
                saveVertexLocation();
                workPanel.validate();
                workPanel.repaint();
                rePaint();
                select = "";
                edgeFlag = false;
                historyModel.add(0, "Удалена вершина: " + vertex.getNumber());
                saveState();
                return;
            }
        }
    }

    public class AddVertexListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addNewVer();
            setFocus();
        }
    }

    public class DragListener extends MouseInputAdapter {
        private Point location;
        private MouseEvent pressed;
        private int oldX = 0;
        private int oldY = 0;

        @Override
        public void mousePressed(MouseEvent e) {
            pressed = e;
            oldX = e.getComponent().getX();
            oldY = e.getComponent().getY();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Component component = e.getComponent();
            location = component.getLocation(location);
            int x = location.x - pressed.getX() + e.getX();
            int y = location.y - pressed.getY() + e.getY();
            if (x + component.getWidth() > workPanel.getWidth() + workPanel.getX() || x < 0) {
                return;
            }
            if (y + component.getHeight() > workPanel.getHeight() || y < 0) {
                return;
            }
            component.setLocation(x, y);
            workPanel.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (oldX != e.getComponent().getX() && oldY != e.getComponent().getY()) {
                saveState();
            }
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
                        select = current.getNumber();
                    }
                }
                edgeFlag = true;
                component.setIcon(new ImageIcon("selectedVer.png"));
                workPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        edgeFlag = false;
                        edge.getStart().resetIcon();
                        select = "";
                    }
                });
            } else {
                if (edge.getStart() == null) {
                    edgeFlag = false;
                    return;
                }
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
                select = "";
                workPanel.addEdge(edge);
                saveVertexLocation();
                workPanel.validate();
                rePaint();
                historyModel.add(0, "Добавлено новое ребро: от вершины " + edge.getStart().getNumber() + " к вершине " + edge.getEnd().getNumber());
                saveState();
                setFocus();
            }
            setFocus();
        }
    }

    private class MyKeyListener extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            //back
            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
                ctrlZ();
            }
            //forward
            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y) {
                ctrlY();
            }
            //add new vertex
            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
                addNewVer();
            }
            //clear workPanel
            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_C) {
                clearState();
                saveState();
            }
            //delete vertex
            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_X) {
                deleteVertex();
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
            setFocus();
        }
    }
}
