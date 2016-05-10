package application;

import javax.swing.*;

/**
 * Created by dmitry on 06.05.16.
 */
public class SelectedDecorator extends VertexDecorator {

    public SelectedDecorator(MyComponent vertex) {
        super(vertex);
    }

    @Override
    public void draw() {
        Vertex ver = (Vertex) vertex;
        ver.getIcon().setIcon(new ImageIcon("images/selectedVer.png"));
    }
}
