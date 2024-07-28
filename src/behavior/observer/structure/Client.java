package behavior.observer.structure;

public class Client {

    public static void main(String[] args) {
        Subject subject = new SubjectImpl();

        Observer observer1 = new ConcreteObserverA();
        Observer observer2 = new ConcreteObserverB();

        subject.register(observer1);
        subject.register(observer2);

        subject.notifyObserver();
    }

}
