package behavior.iterator.example;

import java.util.Iterator;
import java.util.List;

public class ListPostIterator implements Iterator<Post> {
    private Iterator<Post> iter;

    public ListPostIterator(List<Post> posts) {
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
