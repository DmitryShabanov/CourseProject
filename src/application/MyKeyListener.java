package application;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by dmitry on 07.05.16.
 */
public class MyKeyListener extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        //back
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
            GraphBuilder.getInstance().ctrlZ();
        }
        //forward
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y) {
            GraphBuilder.getInstance().ctrlY();
        }
        //add new vertex
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
            GraphBuilder.getInstance().addNewVer();
        }
        //clear workPanel
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_C) {
            GraphBuilder.getInstance().clearState();
            GraphBuilder.getInstance().saveState();
        }
        //delete vertex
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_X) {
            GraphBuilder.getInstance().deleteVertex();
        }
    }
}
