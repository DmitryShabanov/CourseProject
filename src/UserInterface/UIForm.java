package UserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by dmitry on 10.04.16.
 */
public class UIForm extends JFrame {

    private JPanel contentPanel;
    private JToolBar toolBar;
    private JButton addVertex;
    private JButton addEdge;
    private JPanel workPanel;
    private Vertex vertex = new Vertex(workPanel);
    private ArrayList<Vertex> vertexArray = new ArrayList<>();
    private ArrayList<Point> pointArray = new ArrayList<>();

    public UIForm() throws HeadlessException {
        super("Graph Builder");
        setContentPane(contentPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 550);
        setLocationRelativeTo(null);
        addMenu();
        AddListener l = new AddListener();
        addVertex.addActionListener(l);

        setVisible(true);
    }

    public void rePaint() {
        int counter = 0;
        for (Point current : pointArray) {
            vertexArray.get(counter).setLocation(current);
            counter++;
        }
    }

    public class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            pointArray = new ArrayList<>();
            if (vertexArray.size() > 0) {
                for (Vertex current : vertexArray) {
                    pointArray.add(current.getLocation());
                }
            }
            Vertex newVer = vertex.clone();
            newVer.setNumber(vertexArray.size());
            newVer.draw();
            vertexArray.add(newVer);
            rePaint();

        }
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
