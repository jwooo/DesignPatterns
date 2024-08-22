package behavior.memento.structure;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {

    private List<Memento> mementos = new ArrayList<>();

    public void add(Memento memento) {
        this.mementos.add(memento);
    }

    public Memento get(int index) {
        return this.mementos.get(index);
    }

}
