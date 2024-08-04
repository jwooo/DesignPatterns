package behavior.observer.structure;

import java.util.ArrayList;
import java.util.List;

public class SubjectImpl implements Subject {

    private List<Observer> observers = new ArrayList<>();

    @Override
    public void register(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for (Observer o : observers) {
            o.update();
        }
    }

}
