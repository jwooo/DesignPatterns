package creational.factorymethod.ship;

public class Ship {
	public String name, color, capacity;

	@Override
	public String toString() {
		return String.format("Ship { name: %s, color: %s, capacity: %s }", name, color, capacity);
	}
}
