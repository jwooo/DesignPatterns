package creational.abstractfactory.factory;

import creational.abstractfactory.product.keyboard.KeyBoard;
import creational.abstractfactory.product.mouse.Mouse;

public interface AbstractFactory {
	KeyBoard createKeyBoard();
	Mouse createMouse();
}
