package behavior.state.example;

public class Client {
    public static void main(String[] args) {
        LaptopContext laptop = new LaptopContext();
        laptop.currentStatePrint();

        laptop.powerButtonPush();
        laptop.currentStatePrint();
        laptop.typeButtonPush();

        laptop.setSavingState();
        laptop.currentStatePrint();

        laptop.powerButtonPush();
        laptop.currentStatePrint();

        laptop.powerButtonPush();
        laptop.currentStatePrint();
    }
}
