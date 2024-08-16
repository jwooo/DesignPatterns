package behavior.memento.structure;

public class Client {

    public static void main(String[] args) {
        Originator originator = new Originator();
        CareTaker careTaker = new CareTaker();

        originator.setState("Waiting");

        careTaker.add(originator.createMemento());
        System.out.println("[STATE-1] " + originator.getState());
        System.out.println("[VALUE-1] " + careTaker.get(0).getState());

        originator.setState("Ready");

        careTaker.add(originator.createMemento());
        System.out.println("[STATE-2] " + originator.getState());
        System.out.println("[VALUE-2] " + careTaker.get(0).getState());
        System.out.println("[VALUE-2] " + careTaker.get(1).getState());

        originator.setState("Running");

        careTaker.add(originator.createMemento());
        System.out.println("[STATE-3] " + originator.getState());
        System.out.println("[VALUE-3] " + careTaker.get(0).getState());
        System.out.println("[VALUE-3] " + careTaker.get(1).getState());

        // 이전 상태로 되돌림
        originator.restore(careTaker.get(0));
        System.out.println("[STATE-4] " + originator.getState());
        System.out.println("[VALUE-4] " + careTaker.get(0).getState());
    }

}
