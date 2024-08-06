package behavior.mediator.example;

import java.util.ArrayList;
import java.util.List;

public class UserMessageMediator implements Mediator {

    private List<User> users = new ArrayList<>();

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public void sendMessage(User user, String message) {
        users.forEach(receiver -> {
            if (receiver != user) receiver.receive(message);
        });
    }

}
