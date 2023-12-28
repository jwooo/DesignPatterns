package behavior.state.example;

public class OffState implements PowerState {
    @Override
    public void powerButtonPush(LaptopContext context) {
        System.out.println("노트북 전원 ON");
        context.changeState(new OnState());
    }

    @Override
    public void typeButtonPush() {
        throw new IllegalArgumentException("노트북이 전원 OFF인 상태");
    }

    @Override
    public String toString() {
        return "노트북이 전원 OFF인 상태입니다.";
    }
}
