package behavior.mediator.example;

public class Client {

    public static void main(String[] args) {
        Mediator mediator = new UserMessageMediator();

        User creator = new Creator(mediator, "userA");
        User normalUser = new NormalUser(mediator, "userB");
        User normalUser2 = new NormalUser(mediator, "userC");

        mediator.addUser(creator);
        mediator.addUser(normalUser);
        mediator.addUser(normalUser2);

        creator.send("Hello, EveryOne!");
        normalUser.send("Hi! Creator!");
    }

}
