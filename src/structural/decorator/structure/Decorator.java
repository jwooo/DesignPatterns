package structural.decorator.structure;

public class Decorator implements Component {

	Component component;

	Decorator(Component component) {
		this.component = component;
	}

	@Override
	public void operation() {
		component.operation();
	}
}
