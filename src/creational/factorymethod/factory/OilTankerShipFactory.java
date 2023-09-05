package creational.factorymethod.factory;

import creational.factorymethod.ship.OilTankerShip;
import creational.factorymethod.ship.Ship;

public class OilTankerShipFactory implements ShipFactory {

	private OilTankerShipFactory() {}

	private static class SingleInstanceHolder {
		private static final OilTankerShipFactory INSTANCE = new OilTankerShipFactory();
	}

	public static OilTankerShipFactory getInstance() {
		return SingleInstanceHolder.INSTANCE;
	}

	@Override
	public Ship createShip() {
		return new OilTankerShip("OilTankerShip", "15t", "blue");
	}
}
