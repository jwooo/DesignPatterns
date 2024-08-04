package behavior.observer.structure;

public class ConcreteObserverB implements Observer {

    @Override
    public void update() {
        System.out.println("ConcreteObserverB Event");
    }

}
