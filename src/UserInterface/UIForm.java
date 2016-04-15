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

    public class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Vertex newVer = vertex.clone();
            newVer.setNumber(vertexArray.size());
            newVer.draw();
            vertexArray.add(newVer);
        }
    }

    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem menuItem = new JMenuItem("Add vertex");

        menu.add(menuItem);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
    }
}
