package behavior.observer.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.BoundedRangeModel;

public class WeatherAPI implements Subject {

    private Double temp;
    private Double humidity;
    private Double pressure;
    private List<Observer> subcribes = new ArrayList<>();

    @Override
    public void register(Observer o) {
        subcribes.add(o);
    }

    @Override
    public void unregister(Observer o) {
        subcribes.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : subcribes) {
            o.display(this);
        }
    }

    public void measurementsChanged() {

        temp = new Random().nextDouble() * 100;
        humidity = new Random().nextDouble() * 100;
        pressure = new Random().nextDouble() * 100;

        notifyObservers();
    }

    public Double getTemp() {
        return temp;
    }

    public Double getHumidity() {
        return humidity;
    }

    public Double getPressure() {
        return pressure;
    }

}
