package behavior.iterator.wrong_example;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        Board board = new Board();

        board.addPost("board1", LocalDate.of(2020, 8, 30));
        board.addPost("board2", LocalDate.of(2020, 2, 6));
        board.addPost("board3", LocalDate.of(2020, 6, 1));
        board.addPost("board4", LocalDate.of(2021, 12, 22));

        List<Post> posts = board.getPosts();

        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            System.out.println(post.title + "/" + post.date);
        }

        Collections.sort(posts, (p1, p2) -> p1.date.compareTo(p2.date));
        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            System.out.println(post.title + "/" + post.date);
        }

    }
}
