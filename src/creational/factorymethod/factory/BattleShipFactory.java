package creational.factorymethod.factory;

import creational.factorymethod.ship.BattleShip;
import creational.factorymethod.ship.Ship;

public class BattleShipFactory implements ShipFactory {

	private BattleShipFactory() {}

	private static class SingleInstanceHolder {
		private static final BattleShipFactory INSTANCE = new BattleShipFactory();
	}

	public static BattleShipFactory getInstance() {
		return SingleInstanceHolder.INSTANCE;
	}

	@Override
	public Ship createShip() {
		return new BattleShip("BattleShip", "10t", "black");
	}
}
