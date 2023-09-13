package structural.composite.structure;

public class Client {
	public static void main(String[] args) {
		Composite composite1 = new Composite();

		Leaf leaf1 = new Leaf();
		Composite composite2 = new Composite();

		composite1.add(leaf1);
		composite1.add(composite2);

		Leaf leaf2 = new Leaf();
		Leaf leaf3 = new Leaf();
		Leaf leaf4 = new Leaf();

		composite2.add(leaf2);
		composite2.add(leaf3);
		composite2.add(leaf4);

		composite1.operation();
	}
}
