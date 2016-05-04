package application;

import java.util.ArrayList;

/**
 * Created by dmitry on 03.05.16.
 */
public class History {

    private ArrayList<Memento> states = new ArrayList<>();

    public void addState(Memento state, int index) {
        if (index < states.size()) {
            for (int i = states.size() - 1; i >= index; i--) {
                states.remove(states.get(i));
            }
        }
        states.add(state);
    }

    public Memento getState(int index) {
        if (index < 0 || index >= states.size()) {
            return null;
        }
        return states.get(index);
    }
}
