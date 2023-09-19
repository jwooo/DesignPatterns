package structural.decorator.structure;

public class ConcreteDecorator2 extends Decorator {

	ConcreteDecorator2(Component component) {
		super(component);
	}

	public void operation() {
		super.operation();
		extraOperation();
	}

	private void extraOperation() {}
}
