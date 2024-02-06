package behavior.strategy.example;

public class Sword implements Weapon {
    @Override
    public void offensive() {
        System.out.println("검을 휘두릅니다.");
    }
}
