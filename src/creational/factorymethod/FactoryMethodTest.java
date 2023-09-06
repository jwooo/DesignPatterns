package creational.factorymethod;

import creational.factorymethod.factory.BattleShipFactory;
import creational.factorymethod.factory.ContainerShipFactory;
import creational.factorymethod.factory.OilTankerShipFactory;
import creational.factorymethod.ship.Ship;

public class FactoryMethodTest {
	public static void main(String[] args) {
		Ship containerShip = ContainerShipFactory.getInstance().orderShip("jwooo.naver.com");
		System.out.println(containerShip);

		Ship oilTankerShip = OilTankerShipFactory.getInstance().orderShip("jwooo.naver.com");
		System.out.println(oilTankerShip);

		Ship battleShip = BattleShipFactory.getInstance().orderShip("jwooo.naver.com");
		System.out.println(battleShip);
	}
}
