package Graph;

/**
 * Created by dmitry on 05.04.16.
 */
public class Graph {

    private int[][] vertexArray;
    private int[][] parentArray;

    public void initGraph(int[][] vertexArray) {
        this.vertexArray = new int[vertexArray.length][vertexArray.length];
        parentArray = new int[vertexArray.length][vertexArray.length];
        for (int i = 0; i < vertexArray.length; i++) {
            for (int j = 0; j < vertexArray.length; j++) {
                this.vertexArray[i][j] = (vertexArray[i][j] == 0 ? Integer.MAX_VALUE : vertexArray[i][j]);
                parentArray[i][j] = i;
            }
        }
    }

    public void show() {
        System.out.println();
        for (int i = 0; i < vertexArray.length; i++) {
            for (int j = 0; j < vertexArray.length; j++) {
                if (vertexArray[i][j] == Integer.MAX_VALUE || i == j)
                    System.out.print(0 + "  ");
                else
                    System.out.print(vertexArray[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public void floyd() {
        for (int k = 0; k < vertexArray.length; k++)
            for (int i = 0; i < vertexArray.length; i++) {
                for (int j = 0; j < vertexArray.length; j++) {
                    if (vertexArray[i][k] == Integer.MAX_VALUE || vertexArray[k][j] == Integer.MAX_VALUE)
                        continue;
                    if (vertexArray[i][j] > vertexArray[i][k] + vertexArray[k][j]) {
                        vertexArray[i][j] = vertexArray[i][k] + vertexArray[k][j];
                        parentArray[i][j] = parentArray[k][j];
                    }
                }
            }
    }

    private void getWay(int current, int start) {
        if (current == start) {
            System.out.print(current + 1 + " ");
        } else {
            getWay(parentArray[start][current], start);
            System.out.print(current + 1 + " ");
        }
    }

    public void printWay(int start, int finish) {
        start--;
        finish--;
        System.out.print("\nWeight " + (start + 1) + " to " + (finish + 1) + " = ");
        if (vertexArray[start][finish] < Integer.MAX_VALUE) {
            System.out.println(vertexArray[start][finish]);
            getWay(finish, start);
        } else {
            System.out.println("Not exists a way!");
        }
    }

}
