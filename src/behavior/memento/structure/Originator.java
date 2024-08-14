package behavior.memento.structure;

public class Originator {

    private String state;

    public Memento createMemento() {
        return new Memento(state);
    }

    public void restore(Memento memento) {
        state = memento.getState();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
