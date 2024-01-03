package behavior.strategy.example;

public class CrossBow implements Weapon {
    @Override
    public void offensive() {
        System.out.println("석궁을 발사합니다.");
    }
}
