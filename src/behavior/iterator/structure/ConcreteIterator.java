package behavior.iterator.structure;

public class ConcreteIterator implements Iterator {
    Object[] arr;
    private int nextIndex = 0;

    public ConcreteIterator(Object[] arr) {
        this.arr = arr;
    }

    @Override
    public boolean hasNext() {
        return nextIndex < arr.length;
    }

    @Override
    public Object next() {
        return arr[nextIndex++];
    }
}
