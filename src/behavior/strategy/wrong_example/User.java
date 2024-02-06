package behavior.strategy.wrong_example;

public class User {
    public static void main(String[] args) {
        TakeWeapon hand = new TakeWeapon();

        hand.setWeapon(TakeWeapon.SWORD);
        hand.attack();

        hand.setWeapon(TakeWeapon.SHIELD);
        hand.attack();
    }
}
