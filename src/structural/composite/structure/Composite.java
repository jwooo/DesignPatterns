package structural.composite.structure;

import java.util.ArrayList;
import java.util.List;

public class Composite implements Component {
	List<Component> components = new ArrayList<>();

	public void add(Component c) {
		components.add(c);
	}

	public void remove(Component c) {
		components.remove(c);
	}

	@Override
	public void operation() {
		System.out.println(this + " 호출");

		for (Component component : components) {
			component.operation();
		}
	}

	public List<Component> getChild() {
		return components;
	}
}
