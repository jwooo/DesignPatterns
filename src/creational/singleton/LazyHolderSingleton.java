package creational.singleton;

public class LazyHolderSingleton {
	private LazyHolderSingleton() {}

	private static class InnerInstanceClass {
		private static final LazyHolderSingleton instance = new LazyHolderSingleton();
	}

	public static LazyHolderSingleton getInstance() {
		return InnerInstanceClass.instance;
	}
}
