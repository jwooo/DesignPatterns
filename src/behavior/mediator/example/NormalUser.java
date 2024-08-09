package behavior.mediator.example;

public class NormalUser extends User {

    private String username;

    public NormalUser(Mediator mediator, String username) {
        super(mediator);
        this.username = username;
    }

    @Override
    void receive(String message) {
        System.out.println(username + " Got Message / " + message);
    }

    @Override
    void send(String message) {
        mediator.sendMessage(this, this.username + ": " + message);
    }

}
