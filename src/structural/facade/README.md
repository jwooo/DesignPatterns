# FACADE PATTERN

## 퍼사드 패턴이란? 
- 사용하기 복잡한 클래스 라이브러리에 대해 사용하기 편하게 간편한 인터페이스를 구성하기 위한 구조 패턴입니다. 
- 즉, 퍼사드 패턴은 복잡하게 얽혀있는 것을 정리해서 사용하기 편한 인터페이스를 고객에게 제공한다고 보면 됩니다. 

## 퍼사드 패턴 구조
### Facade
- 서브 시스템 기능을 편리하게 사용할 수 있도록 하기 위해 여러 시스템과 상호 작용하는 복잡한 로직을 재정리해서 높은 레벨의 인터페이스를 구성합니다. 
- `Facade`의 역할은 서브 시스템의 많은 역할에 대해 단순한 창구가 되어 주며 클라이언트와 서브 시스템이 서로 긴밀하게 연결되지 않도록 합니다. 
### Additional Facade
- 퍼사드 클래스는 반드시 한 개만 존재해야 한다는 규칙같은 것은 없습니다. 
- 연관되지 않은 기능으 있다면 얼마든지 퍼사드 2세로 분리하고 2세는 다른 퍼사드에서 사용할 수도 있고 클라이언트에서 직접 접근할 수도 있습니다. 
### SubSystem
- 수십 가지 라이브러리 혹은 클래스들
### Client
- 서브 시스템에 직접 접근하는 대신 `Facade`를 사용합니다. 

퍼사드 패턴은 다른 패턴들과 다르게 클래스 구조가 정형화 되지 않는 패턴입니다. 반드시 클래스의 위치가 정해지고 위임받는 형식이 아닌 퍼사드 클래스를 
만들어 적절히 기능 집약화만 해주면 그것이 디자인 패턴이 되는 것입니다. 

### 재귀적인 Facade 패턴의 적용
재귀적 퍼사드란 위에서 언급한 `Addtional Facade`를 말하는 것입니다. 
예를 들어 다수의 클래스, 다수의 패키지를 포함하고 있는 큰 시스템에 요소 요소마다 `Facade` 패턴을 여기저기 적용하고 다시 그 `Facade`를 
합친 `Facade`를 만드는 식으로, 퍼사드를 재귀적으로 구성하면 시스템은 보다 편리하게 됩니다. 

이처럼 퍼사드는 한 개만 있으라는 법은 없으며 필요에 의하면 얼마든지 늘려 의존할 수 있습니다. 

## Facade 패턴 예제

아래 코드는 미리 작성되어 있는 서브시스템 입니다.
```java
public interface Shape {
	void draw();
}

public class Circle implements Shape {
	
	@Override
    public void draw() {
		System.out.println("Circle.draw");
    }
}

public class Rectangle implements Shape {
	
	@Override
    public void draw() {
		System.out.println("Rectangle.draw");
    }
}

public class Square implements Shape {
	
	@Override
    public void draw() {
		System.out.println("Squad.draw");
    }
}
```
위 코드를 파사드 패턴을 이용하여 파사드 클래스 하나로 여러 모양을 생성할 수 있는 방식으로 변경하려고 합니다.


```java
public class ShapeMaker {
	private Shape circle;
	private Shape square;
	private Shape rectangle;

	public ShapeMaker() {
		circle = new Circle();
		square = new Square();
		rectangle = new Rectangle();
	}
	
	public void drawCircle() {
		circle.draw();
    }
	
	public void drawSquare() {
		square.draw();
    }
	
	public void drawRectangle() {
		rectangle.draw();
    }
}
```

```java
public class Client {
	public static void main(String[] args) {
		ShapeMaker shapeMaker = new ShapeMaker();
		
		shapeMaker.drawCircle();
		shapeMaker.drawSquare();
		shapeMaker.drawRectangle();
	}
}
```
위와 같은 과정으로 파사드를 통해 다양한 유형의 모양을 그릴 수 있습니다. 

## 파사드 패턴의 특징
### 패턴 사용 시기
- 시스템이 너무 복잡할 때 
- 간단한 인터페이스를 통해 복잡한 시스템을 접근하도록 하고 싶을때
- 시스템을 사용하고 있는 외부와 결합도가 너무 높을 때 의존성을 낮추기 위할때

### 패턴 장점
- 하위 시스템의 복잡성에서 코드를 분리하여, 외부에서 시스템을 사용하기 쉬워집니다. 
- 하위 시스템 간의 의존 관계가 많을 경우 이를 감소시키고 의존성을 한 곳으로 모읍니다. 
- 복잡한 코드를 감춤으로써, 클라이언트가 시스템의 코드를 모르더라도 `Facade` 클래스만 이해하고 사용 가능합니다. 

### 패턴 단점
- 퍼사드가 앱의 모든 클래스에 결합된 God 객체가 될 수 있습니다. 
- 퍼사드 클래스 자체가 서브시스템에 대한 의존성을 가지게 되어 의존성을 완전히 피할 수는 없습니다. 
- 유지보수 측면에서 공수가 더 많이 들게 됩니다. 
- 따라서 추상화 하고자하는 시스템이 얼마나 복잡한지 퍼사드 패턴을 통해서 얻게 되는 이점과 추가적인 유지보수 비용을 비교해보며 결정하여야 합니다. 