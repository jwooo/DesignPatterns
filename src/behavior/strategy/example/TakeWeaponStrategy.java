package behavior.strategy.example;

public class TakeWeaponStrategy {
    Weapon weapon;

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void attack() {
        weapon.offensive();
    }
}
