package behavior.state.wrong_example;

public class Client {
    public static void main(String[] args) {
        Laptop laptop = new Laptop();
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
