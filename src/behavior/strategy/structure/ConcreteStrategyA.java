package behavior.strategy.structure;

public class ConcreteStrategyA implements Strategy {

    @Override
    public void doSomething() {
        System.out.println("execute ConcreteStrategyA");
    }
}
