package structural.decorator.structure;

public class ConcreteDecorator1 extends Decorator {

	ConcreteDecorator1(Component component) {
		super(component);
	}

	public void operation() {
		super.operation();
		extraOperation();
	}

	private void extraOperation() {}
}
