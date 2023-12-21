package behavior.iterator.structure;

public class ConcreteAggregate implements Aggregate {
    Object[] arr;
    int index = 0;

    public ConcreteAggregate(int size) {
        this.arr = new Object[size];
    }

    public void add(Object o) {
        if (index < arr.length) {
            arr[index] = o;
            index++;
        }
    }

    @Override
    public Iterator iterator() {
        return new ConcreteIterator(arr);
    }
}
