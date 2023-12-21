package behavior.iterator.structure;

public class Client {
    public static void main(String[] args) {
        ConcreteAggregate aggregate = new ConcreteAggregate(5);
        aggregate.add(1);
        aggregate.add(2);
        aggregate.add(3);
        aggregate.add(4);
        aggregate.add(5);

        Iterator iter = aggregate.iterator();

        while (iter.hasNext()) {
            System.out.printf("%s -> ", iter.next());
        }
    }
}
