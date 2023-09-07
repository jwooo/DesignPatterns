package creational.abstractfactory;

import creational.abstractfactory.factory.AbstractFactory;
import creational.abstractfactory.factory.LgFactory;
import creational.abstractfactory.factory.SamsungFactory;

public class Client {
	public static void main(String[] args) {
		AbstractFactory factory;

		factory = new SamsungFactory();
		System.out.println(factory.createKeyBoard().getClass().getName());
		System.out.println(factory.createMouse().getClass().getName());

		factory = new LgFactory();
		System.out.println(factory.createKeyBoard().getClass().getName());
		System.out.println(factory.createMouse().getClass().getName());
	}
}
