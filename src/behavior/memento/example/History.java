package behavior.memento.example;

import java.util.ArrayList;
import java.util.List;

public class History {

    private List<DocumentMemento> mementos = new ArrayList<>();

    public void add(DocumentMemento memento) {
        this.mementos.add(memento);
    }

    public DocumentMemento getMemento(int index) {
        return this.mementos.get(index);
    }

}
