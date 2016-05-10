package application;

import javax.swing.*;

/**
 * Created by dmitry on 06.05.16.
 */
public class SuitedDecorator extends VertexDecorator {

    public SuitedDecorator(MyComponent vertex) {
        super(vertex);
    }

    @Override
    public void draw() {
        Vertex ver = (Vertex) vertex;
        ver.getIcon().setIcon(new ImageIcon("images/suitedVer.png"));
    }
}
