package behavior.iterator.wrong_example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Board {
    List<Post> posts = new ArrayList<>();

    public void addPost(String title, LocalDate date) {
        this.posts.add(new Post(title, date));
    }

    public List<Post> getPosts() {
        return posts;
    }
}
