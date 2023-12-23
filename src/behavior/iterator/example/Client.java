package behavior.iterator.example;

import java.time.LocalDate;
import java.util.Iterator;

public class Client {
    public static void main(String[] args) {
        Board board = new Board();

        board.addPost("board1", LocalDate.of(2020, 8, 30));
        board.addPost("board2", LocalDate.of(2020, 2, 6));
        board.addPost("board3", LocalDate.of(2020, 6, 1));
        board.addPost("board4", LocalDate.of(2021, 12, 22));

        print(board.getListPostIterator());
        print(board.getDatePostIterator());
    }

    public static void print(Iterator<Post> iterator) {
        while (iterator.hasNext()) {
            Post post = iterator.next();
            System.out.println(post.title + "/" + post.date);
        }
        System.out.println();
    }
}
