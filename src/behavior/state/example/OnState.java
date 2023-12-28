package behavior.state.example;

public class OnState implements PowerState {
    @Override
    public void powerButtonPush(LaptopContext context) {
        System.out.println("노트북 전원 OFF");
        context.changeState(new OffState());
    }

    @Override
    public void typeButtonPush() {
        System.out.println("키 입력");
    }

    @Override
    public String toString() {
        return "노트북이 전원 ON인 상태입니다.";
    }
}
