package behavior.state.structure;

public class Client {
    public static void main(String[] args) {
        Context context = new Context();

        context.setState(new ConcreteStateA());
        context.request();

        context.setState(new ConcreteStateB());
        context.request();

        context.request();
    }
}
