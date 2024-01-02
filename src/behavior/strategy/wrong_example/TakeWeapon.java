package behavior.strategy.wrong_example;

public class TakeWeapon {
    public static final int SWORD = 0;
    public static final int SHIELD = 1;
    public static final int CROSSBOW = 2;

    private int state;

    void setWeapon(int state) {
        this.state = state;
    }

    void attack() {
        if (state == SWORD) {
            System.out.println("칼을 휘두르다.");
        } else if (state == SHIELD) {
            System.out.println("방패로 막아섭니다.");
        } else if (state == CROSSBOW) {
            System.out.println("석궁을 발사합니다.");
        }
    }
}
