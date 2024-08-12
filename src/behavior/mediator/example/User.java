package behavior.mediator.example;

public abstract class User {

    protected Mediator mediator;

    public User(Mediator mediator) {
        this.mediator = mediator;
    }

    abstract void receive(String message);
    abstract void send(String message);


}
