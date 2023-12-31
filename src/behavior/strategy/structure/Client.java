package behavior.strategy.structure;

public class Client {
    public static void main(String[] args) {
        Context context = new Context();

        context.setStrategy(new ConcreteStrategyA());
        context.doSomething();

        context.setStrategy(new ConcreteStrategyB());
        context.doSomething();
    }
}
