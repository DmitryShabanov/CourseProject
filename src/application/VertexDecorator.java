package application;

/**
 * Created by dmitry on 06.05.16.
 */
public abstract class VertexDecorator implements MyComponent {

    protected MyComponent vertex;

    public VertexDecorator(MyComponent vertex) {
        this.vertex = vertex;
    }
}
