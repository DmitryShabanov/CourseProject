package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by dmitry on 27.04.16.
 */
public class GraphBuilder extends JFrame {

    protected WorkPanel workPanel = new WorkPanel();
    protected ArrayList<Vertex> vertexes = new ArrayList<>();
    protected ArrayList<Point> points = new ArrayList<>();

    protected Edge edge;
    protected boolean edgeFlag = false;

    protected JFormattedTextField start = new JFormattedTextField(0);
    protected JFormattedTextField finish = new JFormattedTextField(0);
    protected JTextField resultField = new JTextField();

    protected DefaultListModel historyModel = new DefaultListModel();
    protected JList historyList = new JList(historyModel);
    protected JScrollPane historyScrollPane = new JScrollPane(historyList);
    protected History history = new History();
    protected int currentState = -1;

    protected String select = "";
    protected ArrayList<String> numbers = new ArrayList<>();

    private static GraphBuilder instance;

    private GraphBuilder() throws HeadlessException {
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
        workPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (select.compareTo("") != 0) {
                    edgeFlag = false;
                    MyComponent decorator = new ResetDecorator(edge.getStart());
                    decorator.draw();
                    select = "";
                }
                setFocus();
            }
        });
        setFocus();

        setVisible(true);
    }

    public static GraphBuilder getInstance() {
        if (instance == null) {
            instance = new GraphBuilder();
        }
        return instance;
    }

    public void setFocus() {
        workPanel.setFocusable(true);
        workPanel.requestFocusInWindow();
    }

    public void saveVertexLocation() {
        points = new ArrayList<>();
        if (vertexes.size() > 0) {
            for (Vertex current : vertexes) {
                points.add(current.getLocation());
            }
        }
    }

    public void saveState() {
        ArrayList<String> historyLog = new ArrayList<>();
        for (int i = 0; i < historyModel.size(); i++) {
            historyLog.add((String) historyModel.get(i));
        }
        Memento state = new Memento(vertexes, workPanel.getEdges(), historyLog);
        currentState++;
        history.addState(state, currentState);
    }

    public boolean loadState(int index) {
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

    public void clearState() {
        points.clear();
        vertexes.clear();
        resultField.setText("");
        workPanel.getEdges().clear();
        workPanel.removeAll();
        workPanel.validate();
        workPanel.repaint();
        historyModel.removeAllElements();
        start.setValue(0);
        finish.setValue(0);
        historyModel.add(0, "Рабочее пространство очищено");
    }

    public void rePaint() {
        int counter = 0;
        for (Point current : points) {
            vertexes.get(counter).setLocation(current);
            counter++;
        }
    }

    public void validWay(JFormattedTextField tf) {
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

    public void addToolBar() {
        JToolBar toolBar = new JToolBar();
        JButton addVer = new JButton("Добавить вершину");
        JButton calculateWay = new JButton("Найти путь");

        addVer.addActionListener(new AddVertexListener());
        calculateWay.addActionListener(new WayListener());
        start.setColumns(2);
        finish.setColumns(2);

        toolBar.add(addVer);
        addVer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        calculateWay.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
        toolBar.add(calculateWay);
        JButton clear = new JButton("Очистить");
        clear.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
        b1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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

    public void ctrlY() {
        if (loadState(currentState + 1)) {
            currentState++;
        }
        loadState(currentState);
    }

    public void ctrlZ() {
        if (loadState(currentState - 1)) {
            currentState--;
        }
        loadState(currentState);
    }

    public void addHistory() {
        historyScrollPane.setPreferredSize(new Dimension(this.getWidth(), 100));
        this.add(historyScrollPane, BorderLayout.SOUTH);
        historyList.setLayoutOrientation(JList.VERTICAL);
    }

    public void addVertex(Vertex vertex) {
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

    public void addNewVer() {
        clearVertexes();
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

    public void sortNumbers() {
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

    public void clearVertexes() {
        for (Vertex current : vertexes) {
            MyComponent decorator = new ResetDecorator(current);
            decorator.draw();
        }
    }

    public void deleteVertex() {
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
}
