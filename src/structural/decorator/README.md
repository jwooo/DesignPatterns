# DECORATOR PATTERN

## 데코레이터 패턴이란?
데코레이터 패턴이란 대상 객체에 대한 기능 확장이나 변경이 필요할 때 객체의 결합을 통해 서브 클래싱 대신 쓸 수 있는 유연한 대안 구조 패턴입니다. 

데코레이터 패턴을 이용하면 필요한 추가 기능의 조합을 런타임에서 동적으로 생성할 수 있습니다. 
데코레이터할 대상 객체를 새로운 행동들을 포함한 특수 장식자 객체에 넣어서 행동들을 해당 장식자 객체마다 연결시켜, 
서브 클래스로 구성할때 보다 훨씬 유연하게 기능을 확장할 수 있습니다. 그리고 기능을 구현하는 클래스들을 분리함으로써 수정이 용이해집니다. 

## 데코레이터 패턴 구조
![](https://upload.wikimedia.org/wikipedia/commons/thumb/e/e9/Decorator_UML_class_diagram.svg/400px-Decorator_UML_class_diagram.svg.png)

### Component
- 원본 객체와 장식된 객체 모두를 묶는 역할

### ConcreteComponent
- 원본 객체(데코레이팅 할 객체)

### Decorator
- 추상화된 장식자 클래스 
- 원본 객체를 합성(composition)한 `component` 필드와 인터페이스의 구현 메서드를 가지고 있습니다. 

### ConcreteDecorator
- 구체적인 장식자 클래스 
- 부모 클래스가 감싸고 있는 하나의 `Component`를 호출하면서 호출 전/후로 부가적인 로직을 추가할 수 있습니다.

## 데코레이터 패턴 구성
```java
// 원본 객체와 장식된 객체 모두를 묶는 인터페이스 
interface Component {
	void operation();
}

// 장식될 원본 객체
class ConcreteComponent implements Component {
	public void operation() {}
}

// 장식자 추상 클래스 
abstract class Decorator implements Component {
	Component component;
	
	Decorator(Component component) {
		this.component = component;
    }
	
	public void operation() {
		component.operation();
    }
}

// 장식자 클래스 
class ConcreteDecorator1 extends Decorator {
	ConcreteDecorator1(Component component) {
		super(component);
    }
	
	public void operation() {
		super.operation();
		extraOperation();
    }
	
	void extraOperation() {}
}

// 장식자 클래스 
class ConcreteDecorator2 extends Decorator {
	ConcreteDecorator2(Component component) {
		super(component);
    }
	
	public void operation() {
		super.operation();
		extraOperaion();
    }
	
	void extraOperation() {}
}
```

```java
public class Client {
	public static void main(String[] args) {
        Component obj = new ConcreteComponent();
		
		Component decorator1 = new ConcreteDecorator1(obj);
		decorator1.operation();
		
		Component decorator2 = new ConcreteDecorator2(obj);
		decorator2.operation();
		
		Conponent decorator3 = new ConcreteDecorator1(new ConcreteDecorator2(obj));
	}
}
```

## 데코레이터 패턴 특징
### 패턴 사용 시기
- 객체의 책임과 행동이 동적으로 상황에 따라 다양한 기능이 빈번하게 추가/삭제되는 경우
- 객체의 결합을 통해 기능이 생성될 수 있는 경우
- 객체를 사용하는 코드를 손상시키지 않고 런타임에 객체에 추가 동작을 할당할 수 있어야 하는 경우 
- 상속을 통해 서브 클래싱으로 객체의 동작을 확장하는 것이 어색하거나 불가능한 경우

### 패턴 장점
- 데코레이터를 사용하면 서브 클래스를 만들때 보다 훨씬 더 유연하게 기능을 확장할 수 있습니다. 
- 객체를 여러 데코레이터로 래핑하여 여러 동작을 결합할 수 있습니다. 
- 컴파일 타임이나 아닌 런타임에 동적으로 기능을 변경할 수 있습니다. 
- 각 장식자 클래스마다 고유의 책임을 가져 단일 책임 원칙(SRP)을 준수할 수 있습니다. 
- 클라이언트 코드 수정없이 기능 확장이 필요하면 장식자 클래스를 추가하면 되니 개방 폐쇄 원칙(OCP)를 준수할 수 있습니다. 
- 구현체가 아닌 인터페이스를 바라봄으로써 의존 역전 원직(DIP)를 준수할 수 있습니다. 

### 패턴 단점 
- 만일 장식자 일부를 제거하고 싶다면, `Wrapper` 스택에서 특정 `wrapper`를 제거하는 것은 어렵습니다. 
- 데코레이터를 조합하는 초기 생성코드가 보기 안좋을 수 있습니다. 
- 어느 장식자를 먼저 데코레이팅 하느냐에 따라 데코레이터 스택 순서가 결정지게 되는데, 만일 순서에 의존하지 않는 방식으로 데코레이터를 구현하기는 어렵습니다. 
