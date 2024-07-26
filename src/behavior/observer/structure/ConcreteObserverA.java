package behavior.observer.structure;

public class ConcreteObserverA implements Observer {

    @Override
    public void update() {
        System.out.println("ConcreteObserverA Event");
    }

}
