package structural.adapter.objectadapter;

public class Adapter implements Target {
	Service adaptee;

	Adapter(Service adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void method(int data) {
		adaptee.specificMethod(data);
	}
}
