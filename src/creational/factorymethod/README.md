# FACTORY METHOD PATTERN


## 팩토리 메서드 패턴이란?
팩토리 메서드 패턴은 객체 생성을 팩토리 클래스로 캡슐화 처리하여 대신 생성하게 하는 생성 디자인 패턴입니다. 
즉, 클라이언트에서 직접 `new` 연산자를 통해 제품 객체를 생성하는 것이 아닌, 제품 객체들도 도맡아 생성하는 공장 클래스를 만들고, 
이를 상속하는 서브 공장 클래스의 메서드에서 여러가지 제품 객체 생성을 각각 챔임 지는 것입니다. 

또한 객체 생성에 필요한 과정을 템플릿처럼 미리 구성해놓고, 객체 생성에 관한 전처리나 후처리를 통해 생성 과정을 다양하게 처리하여 객체를 유연하게
정의할 수 있는 특징을 가지고 있습니다. 

## 팩토리 메서드 패턴 예제 
예제로 선박 제품을 만드는 로직을 구성한다고 가정하자. 

### 클린하지 않은 문제의 코드
가장 심플한 방법은 Ship 객체를 만들어 반환하는 메서드를 정의하는 것입니다. 
매개변수의 입력 값에 따라 각기 다른 타입의 선박도 분기문으로 생성하였습니다. 

```java
class Ship {
	String name, color, capacity;
	
	@Override
    public String toString() {
		return String.format("Ship { name: %s, color: %s, capacity: %s }", name, color, capacity);
    }
}
```
```java
public static Ship orderShip(String name, String email) {
	if (name == null) {
		throw new IllegalArgumentException("배 이름을 지어주세요");
    } 
	if (email == null) {
		throw new IllegalArgumentException("이메일을 남겨주세요");
    }
	
	Ship ship = new Ship();
	
	ship.name = name;
	
	if (name.equalsIgnoreCase("ContainerShip")) {
		ship.capacity = "20t";
    } else if (name.equalsIgnoreCase("OilTankerShip")) {
		ship.capacity = "15t";
    }
	
	if (name.equalsIgnoreCase("ContainerShip")) {
		ship.color = "green";
    } else if (name.equalsIgnoreCase("OilTankerShip")) {
		ship.color = "blue";
    }
	
	System.out.print(ship.name + " 다 만들었다고 " + email + "로 메일을 보냈습니다.");
	
	return ship;
}

public static void main(String[] args) {
	Ship containerShip = orderShip("ContainerShip", "jwooo.naver.com");
	System.out.println(containerShip);
	
	Ship oilTankerShip = orderShip("OilTankerShip", "jwooo.naver.com");
	System.out.println(oilTankerShip);
}
```
하지만 위의 방법은 클린하지 않은 코드입니다. 

만일 컨테이너 선박이나 유조선 외에 다른 종류의 선박들이 계속 추가 된다면 분기문이 선형적으로 늘어나 복잡해질 것입니다. 
또한 향후에 Ship 클래스 구성 자체가 변화한다면 분기문 로직도 통째로 바꿔줘야 할 것입니다. 

### 팩토리 메서드 패턴을 적용하는 방법
```java
class Ship {
	String name, color, capacity;
	
	@Override
    public String toString() {
		return String.format("Ship { name: %s, color: %s, capacity: %s }", name, color, capacity);
    }
}

class ContainerShip extends Ship {
	ContainerShip(String name, String color, String capacity) {
		this.name = name;
		this.color = color;
		this.capacity = capacity;
    }
}

class OilTankerShip extends Ship {
	OilTankerShip(String name, String color, String capacity) {
		this.name = name;
		this.color = color;
		this.capacity = capacity;
    }
}
```
```java
abstract class ShipFactory {
	final Ship orderShip(String email) {
		validate(email);
		
		Ship ship = createShip();
		
		sendEmailTo(email, ship);
		
		return ship;
    }
	
	abstract protected Ship createShip();
	
	private void validate(String email) {
		if (email == null) {
			throw new IllegalArgumentException("이메일을 남겨주세요");
		}
    }
	
	private void sendEmailTo(String email, String ship) {
		System.out.println(ship.name + " 다 만들었다고 " + email + "로 메일을 보냈습니다.");
    }
}

class ContainerShipFactory extends ShipFactory {
	@Override
    protected Ship createShip() {
		return new ContainerShip("ContainerShip", "20t", "green");
    }
}

class OilTankerShipFactory extends ShipFactory { 
	@Override
    protected Ship createShip() {
		return new OilTankerShip("OilTankerShip", "15t", "blue");
    }
}
```
```java
class Client {
	Ship containerShip = new ContainerShipFactory.orderShip("jwooo.naver.com");
	System.out.println(containerShip);
	
	Ship oilTankerShip = new OilTankerShipFactory.orderShip("jwooo.naver.com");
	System.out.println(oilTankerShip);
	
}
```

선박 타입명을 입력 값으로 줘서 분기문을 통해 선박 제품을 생성하는 것이 아닌, 전용 선박 생산 공장 객체를 통해 선박을 생성함으로써, 수정에 닫혀있고 
확장에 열려있는 구조를 구성할 수 있습니다. 

만일 BattleShip이 추가 된다고 가정하면, 간단하게 제품 객체와 공장 객체를 각각 정의하고 상속 시키기만 하면 기존에 작성했던 코드 수정없이 확장 됩니다. 
```java
class BattleShip {
	BattleShip(String name, String color, String capacity) {
		this.name = name;
		this.color = color;
		this.capacity = capacity;
    }
}

class BattleShipFactory extends ShipFactory {
	@Override
    protected Ship createShip() {
		return new BattleShip("BattleShip", "10t", "black");
    }
}
```

### 팩토리 객체를 싱글톤화 하기
객체를 생성하는 공장 객체는 여러개 있을 필요성이 없습니다. 
따라서 싱글톤 객체로 구성하여 유일한 인스턴스로 만들어 두는 것이 베스트입니다. 

```java
class ContainerShipFactory extends ShipFactory {
	private ContainerShipFactory() {}
    
    private static class SingleInstanceHolder {
		private static final ContainerShipFactory INSTANCE = new ContainerShipFactory();
    }
	
	public static ContainerShipFactory getInstance() {
		return SingleInstanceHolder.INSTANCE;
    }
	
	@Override
    protected Ship createShip() {
		return new ContainerShip("ContainerShip", "20t", "green");
    }
}
```

### 최상위 팩토리를 인터페이스로 구성하기
최상위 팩토리 클래스는 반드시 추상 클래스로 선언할 필요 없습니다. 
Java 8 버전 이후 추가된 인터페이스의 디폴트 메서드와 Java 9 버전 이후 추가된 private 메서드를 통해 그대로 인터페이스로 구성할 수 있기 때문입니다. 

```java
interface ShipFactory {
	default Ship orderShip(String email) {
		validate(email);
		
		Ship ship = createShip();
		
		sendEmailTo(email, ship);
		
		return ship;
    }
	
	Ship createShip();
	
	private void validate(String email) {
		if (email == null) {
			throw new IllegalArgumentException("이메일을 남겨주세요");
		}
    }
	
	private void sendEmailTo(String email, Ship ship) {
		System.out.println(ship.name + " 다 만들었다고 " + email + "로 메일을 보냈습니다.");
    }
}
```