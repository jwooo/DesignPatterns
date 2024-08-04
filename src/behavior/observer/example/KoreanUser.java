package behavior.observer.example;


public class KoreanUser implements Observer {

    private String name;

    public KoreanUser(String name) {
        this.name = name;
    }

    @Override
    public void display(WeatherAPI api) {
        System.out.println("사용자 [" + name + "]: " + "현재 기상 정보 조회" + " => " + api.getTemp() + "C, "
            + api.getHumidity() + "fg/ms %, " + api.getPressure() + "fhPa");
    }

}
