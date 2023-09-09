package structural.adapter.classadapter;

public class Adapter extends Service implements Target {

	@Override
	public void method(int data) {
		specificationMethod(data);
	}
}
