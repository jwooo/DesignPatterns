package creational.singleton;

public class LazyInitSingleton {
	private static LazyInitSingleton instance;

	private LazyInitSingleton() {}

	public static synchronized LazyInitSingleton getInstance() {
		if (instance == null) {
			instance = new LazyInitSingleton();
		}
		return instance;
	}
}
