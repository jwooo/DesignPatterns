package behavior.state.example;

public class SavingState implements PowerState {
    @Override
    public void powerButtonPush(LaptopContext context) {
        System.out.println("노트북 전원 ON");
        context.changeState(new OnState());
    }

    @Override
    public void typeButtonPush() {
        throw new IllegalArgumentException("노트북이 절전 모드인 상태입니다.");
    }

    @Override
    public String toString() {
        return "노트북이 절전모드인 상태입니다.";
    }
}
