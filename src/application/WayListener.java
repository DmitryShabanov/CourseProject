package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by dmitry on 07.05.16.
 */
public class WayListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        GraphBuilder gb = GraphBuilder.getInstance();
        gb.clearVertexes();
        gb.validWay(gb.start);
        gb.validWay(gb.finish);
        if (gb.vertexes.size() == 0) {
            gb.setFocus();
            return;
        }
        if ((int) gb.start.getValue() == (int) gb.finish.getValue()) {
            gb.resultField.setText("не существует!");
            gb.setFocus();
            return;
        }
        int max = 0;
        for (Vertex vertex : gb.vertexes) {
            if (max < Integer.valueOf(vertex.getNumber())) {
                max = Integer.valueOf(vertex.getNumber());
            }
        }
        max = max + 1;
        ArrayList<Integer> result = gb.workPanel.getShortestWay(max, (int) gb.start.getValue(), (int) gb.finish.getValue(), gb.resultField);
        for (Vertex vertex : gb.vertexes) {
            for (Integer current : result) {
                if (Integer.valueOf(vertex.getNumber()) == (int) current) {
                    MyComponent decorator = new SuitedDecorator(vertex);
                    decorator.draw();
                    break;
                }
            }
        }
        gb.setFocus();
    }
}
