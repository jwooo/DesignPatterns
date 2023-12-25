package behavior.iterator.example;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class DatePostIterator implements Iterator<Post> {
    private Iterator<Post> iter;

    public DatePostIterator(List<Post> posts) {
        Collections.sort(posts, (p1, p2) -> p1.date.compareTo(p2.date));
        this.iter = posts.iterator();
    }

    @Override
    public boolean hasNext() {
        return this.iter.hasNext();
    }

    @Override
    public Post next() {
        return this.iter.next();
    }
}
