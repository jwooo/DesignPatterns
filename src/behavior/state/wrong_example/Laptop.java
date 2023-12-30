package behavior.state.wrong_example;

public class Laptop {
    public static final int OFF = 0;
    public static final int SAVING = 1;
    public static final int ON = 2;

    private int powerState;

    Laptop() {
        this.powerState = Laptop.OFF;
    }

    void changeState(int state) {
        this.powerState = state;
    }

    void powerButtonPush() {
        if (powerState == Laptop.OFF) {
            System.out.println("전원 ON");
            changeState(Laptop.ON);
        } else if (powerState == Laptop.ON) {
            System.out.println("전원 OFF");
            changeState(Laptop.OFF);
        } else if (powerState == Laptop.SAVING) {
            System.out.println("전원 ON");
            changeState(Laptop.ON);
        }
    }

    void setSavingState() {
        System.out.println("절전 모드");
        changeState(Laptop.SAVING);
    }

    void typeButtonPush() {
        if (powerState == Laptop.OFF) {
            throw new IllegalArgumentException("노트북이 OFF 인 상태");
        } else if (powerState == Laptop.ON) {
            System.out.println("키 입력");
        } else if (powerState == Laptop.SAVING) {
            throw new IllegalArgumentException("노트북이 절전 모드 입니다.");
        }
    }

    void currentStatePrint() {
        if (powerState == Laptop.OFF) {
            System.out.println("노트북이 전원 OFF인 상태입니다. ");
        } else if (powerState == Laptop.ON) {
            System.out.println("노트북이 전원 ON인 상태입니다.");
        } else if (powerState == Laptop.SAVING) {
            System.out.println("노트북이 절전 모드인 상태입니다.");
        }
    }
}
