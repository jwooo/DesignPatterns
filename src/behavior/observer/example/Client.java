package behavior.observer.example;

public class Client {

    public static void main(String[] args) {
        WeatherAPI api = new WeatherAPI();

        api.register(new KoreanUser("UserA"));
        api.register(new KoreanUser("UserB"));
        api.register(new KoreanUser("UserC"));

        api.measurementsChanged();
    }

}
