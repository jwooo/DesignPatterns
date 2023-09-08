package creational.abstractfactory.factory;

import creational.abstractfactory.product.keyboard.KeyBoard;
import creational.abstractfactory.product.keyboard.LgKeyBoard;
import creational.abstractfactory.product.mouse.LgMouse;
import creational.abstractfactory.product.mouse.Mouse;

public class LgFactory implements AbstractFactory {

	@Override
	public KeyBoard createKeyBoard() {
		return new LgKeyBoard();
	}

	@Override
	public Mouse createMouse() {
		return new LgMouse();
	}
}
