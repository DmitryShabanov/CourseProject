package UserInterface;

import java.util.ArrayList;

/**
 * Created by dmitry on 05.04.16.
 */
public class Graph {

    private int[][] vertexArray;
    private int[][] parentArray;
    private ArrayList<Integer> result = new ArrayList<>();

    public Graph(int[][] vertexArray) {
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

    public ArrayList<Integer> getResult() {
        return result;
    }

    private void getWay(int current, int start) {
        if (current == start) {
            result.add(current);
            //System.out.print(current + " ");
        } else {
            getWay(parentArray[start][current], start);
            //System.out.print(current + " ");
            result.add(current);
        }
    }

    public int printWay(int start, int finish) {
        if (start < 0 || start > vertexArray.length || finish < 0 || finish > vertexArray.length) {
            System.out.println("Not a vertex!");
            return -1;
        }
        //System.out.print("\nWeight " + start + " to " + finish + " = ");
        if (vertexArray[start][finish] < Integer.MAX_VALUE) {
            //System.out.println(vertexArray[start][finish]);
            getWay(finish, start);
            //System.out.println();
        } else {
            //System.out.println("Not exists a way!");
            return -1;
        }
        return vertexArray[start][finish];
    }

}
