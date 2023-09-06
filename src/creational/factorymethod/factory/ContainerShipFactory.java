package creational.factorymethod.factory;

import creational.factorymethod.ship.ContainerShip;
import creational.factorymethod.ship.Ship;

public class ContainerShipFactory implements ShipFactory {

	private ContainerShipFactory() {}

	private static class SingleInstanceHolder {
		private static final ContainerShipFactory INSTANCE = new ContainerShipFactory();
	}

	public static ContainerShipFactory getInstance() {
		return SingleInstanceHolder.INSTANCE;
	}

	@Override
	public Ship createShip() {
		return new ContainerShip("ContainerShip", "20t", "green");
	}
}
