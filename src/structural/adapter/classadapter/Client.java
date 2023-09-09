package structural.adapter.classadapter;

public class Client {
	public static void main(String[] args) {
		Target adapter = new Adapter();

		adapter.method(1);
	}
}
