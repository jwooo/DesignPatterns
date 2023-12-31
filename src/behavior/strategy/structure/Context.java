package behavior.strategy.structure;

public class Context {
    Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void doSomething() {
        strategy.doSomething();
    }
}
