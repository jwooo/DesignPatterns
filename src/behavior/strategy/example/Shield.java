package behavior.strategy.example;

public class Shield implements Weapon {
    @Override
    public void offensive() {
        System.out.println("방패를 밀친다.");
    }
}
