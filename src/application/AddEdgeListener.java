package application;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;

/**
 * Created by dmitry on 07.05.16.
 */
public class AddEdgeListener extends MouseInputAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel component = (JLabel) e.getComponent();
        GraphBuilder.getInstance().clearVertexes();
        if (!GraphBuilder.getInstance().edgeFlag) {
            GraphBuilder.getInstance().edge = new Edge();
            for (Vertex current : GraphBuilder.getInstance().vertexes) {
                if (current.getNumber().compareTo(component.getText()) == 0) {
                    GraphBuilder.getInstance().edge.setStart(current);
                    GraphBuilder.getInstance().select = current.getNumber();
                    MyComponent decorator = new SelectedDecorator(current);
                    decorator.draw();
                }
            }
            GraphBuilder.getInstance().edgeFlag = true;
        } else {
            if (GraphBuilder.getInstance().edge.getStart() == null) {
                GraphBuilder.getInstance().edgeFlag = false;
                return;
            }
            for (Vertex current : GraphBuilder.getInstance().vertexes) {
                if (current.getNumber().compareTo(component.getText()) == 0) {
                    if (component.getX() == GraphBuilder.getInstance().edge.getStart().getLocation().getX() && component.getY() == GraphBuilder.getInstance().edge.getStart().getLocation().getY()) {
                        return;
                    }
                    GraphBuilder.getInstance().edge.setEnd(current);
                }
            }
            GraphBuilder.getInstance().edgeFlag = false;
            MyComponent decorator = new ResetDecorator(GraphBuilder.getInstance().edge.getStart());
            decorator.draw();
            GraphBuilder.getInstance().select = "";
            GraphBuilder.getInstance().workPanel.addEdge(GraphBuilder.getInstance().edge);
            GraphBuilder.getInstance().edge.getWeight().addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    JFormattedTextField field = (JFormattedTextField) e.getComponent();
                    GraphBuilder.getInstance().clearVertexes();
                    GraphBuilder.getInstance().saveState();
                }
            });
            GraphBuilder.getInstance().saveVertexLocation();
            GraphBuilder.getInstance().workPanel.validate();
            GraphBuilder.getInstance().rePaint();
            GraphBuilder.getInstance().historyModel.add(0, "Добавлено новое ребро: от вершины " + GraphBuilder.getInstance().edge.getStart().getNumber() + " к вершине " + GraphBuilder.getInstance().edge.getEnd().getNumber());
            GraphBuilder.getInstance().saveState();
            GraphBuilder.getInstance().setFocus();
        }
        GraphBuilder.getInstance().setFocus();
    }
}
