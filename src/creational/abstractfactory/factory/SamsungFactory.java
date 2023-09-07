package creational.abstractfactory.factory;

import creational.abstractfactory.product.keyboard.KeyBoard;
import creational.abstractfactory.product.keyboard.SamsungKeyBoard;
import creational.abstractfactory.product.mouse.Mouse;
import creational.abstractfactory.product.mouse.SamsungMouse;

public class SamsungFactory implements AbstractFactory {
	@Override
	public KeyBoard createKeyBoard() {
		return new SamsungKeyBoard();
	}

	@Override
	public Mouse createMouse() {
		return new SamsungMouse();
	}
}
