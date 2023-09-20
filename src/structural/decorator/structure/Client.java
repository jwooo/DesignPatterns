package structural.decorator.structure;

public class Client {
	public static void main(String[] args) {
		Component obj = new ConcreteComponent();

		Component decorator1 = new ConcreteDecorator1(obj);
		decorator1.operation();

		Component decorator2 = new ConcreteDecorator2(obj);
		decorator2.operation();

		Component decorator3 = new ConcreteDecorator1(new ConcreteDecorator2(obj));
	}
}
