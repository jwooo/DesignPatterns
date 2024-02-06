package behavior.strategy.example;

public class User {
    public static void main(String[] args) {
        TakeWeaponStrategy hand = new TakeWeaponStrategy();

        hand.setWeapon(new Sword());
        hand.attack();

        hand.setWeapon(new Shield());
        hand.attack();

        hand.setWeapon(new CrossBow());
        hand.attack();
    }
}
