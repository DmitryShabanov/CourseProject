package application;

import javax.swing.*;

/**
 * Created by dmitry on 06.05.16.
 */
public class HoverDecorator extends VertexDecorator {

    public HoverDecorator(MyComponent vertex) {
        super(vertex);
    }

    @Override
    public void draw() {
        Vertex ver = (Vertex) vertex;
        ver.getIcon().setIcon(new ImageIcon("images/hoverVer.png"));
    }
}
