package behavior.state.structure;

public class ConcreteStateB implements AbstractState {
    @Override
    public void requestHandle(Context context) {
        context.setState(new ConcreteStateC());
    }
}
