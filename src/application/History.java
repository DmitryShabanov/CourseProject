package application;

import java.util.ArrayList;

/**
 * Created by dmitry on 03.05.16.
 */
public class History {

    private ArrayList<Memento> states = new ArrayList<>();

    public void addState(Memento state) {
        states.add(state);
    }

    public Memento getState(int index) {
        return states.get(index);
    }

    public Memento getNewestState() {
        return states.get(states.size() - 1);
    }

    public void deleteState(Memento state) {
        states.remove(state);
    }

    public void deleteState(int index) {
        states.remove(index);
    }
}
