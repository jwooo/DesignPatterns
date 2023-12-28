package behavior.state.example;

public class LaptopContext {
    PowerState powerState;

    LaptopContext() {
        this.powerState = new OffState();
    }

    void changeState(PowerState state) {
        this.powerState = state;
    }

    void setSavingState() {
        System.out.println("노트북 절전 모드");
        changeState(new SavingState());
    }

    void powerButtonPush() {
        powerState.powerButtonPush(this);
    }

    void typeButtonPush() {
        powerState.typeButtonPush();
    }

    void currentStatePrint() {
        System.out.println(powerState.toString());
    }
}
