package behavior.state.structure;

public class Context {
    AbstractState state;

    void setState(AbstractState state) {
        this.state = state;
    }

    void request() {
        state.requestHandle(this);
    }
}
