# ABSTRACT FACTORY PATTERN

추상 팩토리 패턴은 연관성이 있는 객체 군이 여러 개 있을 경우 이들을 묶어 추상화하고, 어던 구체적인 상황이 주어지면 팩토리 객체에서 
집합으로 묶은 객체 군을 구현화 하는 생성 패턴입니다. 

클라이언트에서 특정 객체를 사용할 때 팩토리 클래스 만을 참조하여 특정 객체에 대한 구현부를 감추어 역할과 구현을 분리시킬 수 있습니다. 
즉, 추상 팩토리의 핵심은 제품군 집합을 타입 별로 찍어 낼 수 있다는 점이 포인트입니다.

## 추상 팩토리 패턴 흐름

### 제품(Product) 클래스
```java
interface KeyBoard {
}

class SamsungKeyBoard implements KeyBoard {
}

class LgKeyBoard implements KeyBoard {
}
```
```java
interface Mouse {
}

class SamsungMouse implements Mouse {
}

class LgMouse implements Mouse {
}
```
### 공장(Factory) 클래스

```java
import creational.abstractfactory.product.keyboard.LgKeyBoard;
import creational.abstractfactory.product.keyboard.SamsungKeyBoard;

interface AbstractFactory {
	KeyBoard createKeyBoard();
	Mouse createMouse();
}

class SamsungFactory implements AbstractFactory {
	public KeyBoard createKeyBoard() {
		return new SamsungKeyBoard();
	}

	public Mouse createMouse() {
		return new SamsungMouse();
	}
}

class LgFactory implements AbstractFactory {
	public KeyBoard createKeyBoard() {
		return new LgKeyBoard();
	}

	public Mouse createMouse() {
		return new LgMouse();
	}
}
```
### Client에서의 사용
```java
class Client {
	public static void main(String[] args) {
		AbstractFactory factory;

		factory = new SamsungFactory();
		System.out.println(factory.createKeyBoard().getClass().getName());
		System.out.println(factory.createMouse().getClass().getName());

		factory = new LgFactory();
		Computer LgComputer = factory.createComputer();
		System.out.println(factory.createKeyBoard().getClass().getName());
		System.out.println(factory.createMouse().getClass().getName());
    }
}
```
코드를 보면 똑같은 createKeyBoard()와 createMouse() 메서드를 호출하지만 어떤 팩토리 객체이냐에 따라 반환되는 제품군이 다르게 됩니다. 

## 추상 팩토리 패턴 특징
### 패턴 사용 시기
- 관련 제품의 다양한 제품 군과 함께 작동해야 할때, 해당 제품의 구체적인 클래스에 의존하고 싶지 않은 경우
- 여러 제품군 중 하나를 선택해서 시스템을 설정해야하고 한 번 구성한 제품을 다른 것으로 대체할 수도 있을 때 
- 제품에 대한 클래스 라이브러리를 제공하고, 그들의 구현이 아닌 인터페이스를 노출시키고 싶을 때 

### 패턴 장점
- 객체를 생성하는 코드를 분리하여 클라이언트 코드와 결합도를 낮출 수 있습니다. 
- 제품군을 쉽게 대체할 수 있습니다. 
- 단일 책임 원칙 준수
- 개방/폐쇄 원칙 준수 

### 패턴 단점
- 각 구현체마다 팩토리 객체들을 모두 구현해주어야 하기 때문에 객체가 늘어날때 마다 클래스가 증가하여 코드의 복잡성이 증가합니다. 
- 기존 추상 팩토리의 세부사항이 변경되면 모든 팩토리에 대한 수정이 필요해집니다. 이는 추상 팩토리와 모든 서브 클래스의 수정을 가져옵니다. 
- 새로운 종류의 제품을 지원하는 것이 어렵습니다. 새로운 제품이 추가되면 팩토리 구현 로직이 자체를 변경해야 합니다. 

## 팩토리 메서드 패턴 vs 추상 팩토리 패턴
### 공통점
- 객체 생성 과정을 추상화한 인터페이스를 제공합니다. 
- 객체 생성을 캡슐화함으로써 구체적인 타입을 감추고 느슨한 결합 구조를 표방

### 차이점
#### 팩토리 메서드 패턴
- 구체적인 객체 생성 과정을 하위 또는 구체적인 클래스로 옮기는 것이 목적
- 한 팩토리당 한 종류의 객체 생성 지원
- 메서드 레벨에서 포커스를 맞춤으로써, 클라이언트의 인스턴스의 생성 및 구성에 대한 의존을 감소
#### 추상 팩토리 패턴
- 관련있는 여러 객체를 구체적인 클래스에 의존하지 않고 만들 수 있게 해주는 것이 목적
- 한 팩토리에서 서로 연관된 여러 종류의 객체 생성을 지원
- 클래스 레벨에서 포커스를 맞춤으로써, 클라이언트의 인스턴스 군의 생성 및 구성에 대한 의존을 감소