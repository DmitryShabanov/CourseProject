package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by dmitry on 07.05.16.
 */
public class AddVertexListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        GraphBuilder.getInstance().addNewVer();
        GraphBuilder.getInstance().setFocus();
    }
}
