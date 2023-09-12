# BRIDGE PATTERN

## 브릿지 패턴이란? 
- 구현부에서 추상층을 분리하여 각자 독립적으로 변형이 가능하고 확장이 가능하도록 하는 패턴입니다.

## 브릿지 패턴 구조
![](https://upload.wikimedia.org/wikipedia/commons/thumb/c/cf/Bridge_UML_class_diagram.svg/750px-Bridge_UML_class_diagram.svg.png)

### Abstraction
- 기능 계층의 최상위 클래스로 추상 인터페이스를 정의합니다. 
- 구현 부분에 해당하는 클래스 인스턴스를 가지고 해당 인스턴스를 통해 구현 부분의 메서드를 호출합니다. 

### RefinedAbstraction
- `Abstraction`에 의해 정의된 인터페이스를 확장합니다. 
- 기능 계층에서 새로운 부분을 확장한 클래스 입니다. 

### Implementor
- 구현 클래스를 위한 인터페이스를 정의합니다. 
- `Abstraction`의 기능을 구현하기 위한 인터페이스를 정의합니다.

### ConcreteImplementor
- `Implementor` 인터페이스를 구현하여 실제 기능을 구현한다.

## 브릿지 패턴 예제
```java
// Implementor
public interface Work {
	void develop();
	void executeIDE();
}
```
`Implementor`역할을 하는 `Work` 인터페이스는 개발자가 가질 수 있는 `develop()` 메서드와 `executeIDE()` 메서드를 가지고 있습니다. 

```java
// ConcreteImplementor
public class WorkBackEnd implements Work {
	@Override
    public void develop() {
		System.out.println("Java 언어로 개발을 시작합니다.");
    }
	
	@Override
    public void executeIDE() {
		System.out.println("IntelliJ를 실행 시킵니다.");
    }
}

public class WorkFrontEnd implements Work {
	@Override
    public void develop() {
		System.out.println("JavaScript 언어로 개발을 시작합니다.");
    }
	
	@Override
    public void executeIDE() {
		System.out.println("Visual Studio Code를 실행 시킵니다.");
    }
}
```
`Work` 인터페이스를 상속받아 실제 기능에 해당하는 부분을 구현합니다.


```java
// Abstraction
public abstract class Developer {
	protected Work work;
	
	public Developer(Work work) {
		this.work = work;
    }
	
	public abstract void startWork();
}
```
기능 부분에 해당되는 최상위 클래스입니다. 
`Work`의 인스턴스를 가지고 각각의 `Work`를 상속받아 구현하고 있는 메서드들을 호출하고 있습니다.

```java
// RefinedAbstraction
public class BackEndDeveloper extends Developer {
	public BackEndDeveloper(Work work) {
		super(work);
    }
	
	@Override
    public void startWork() {
		System.out.println("백엔드 개발자가 출근하였습니다.");
		work.executeIDE();
		work.develop();
    }
}

public class FrontEndDeveloper extends Developer {
	public FrontEndDeveloper(Work work) {
		super(work);
    }
	
	@Override
    public void startWork() {
		System.out.println("프론트엔드 개발자가 출근하였습니다.");
		work.executeIDE();
		work.develop();
    }
}
```
`Developer` 클래스를 확장한 클래스 입니다. 
`Developer` 를 상속받은 클래스들은 필요한 것을 자신에 맞게 확장할 수 있게 됩니다.

```java
public class Client {
	public static void main(String[] args) {
		Developer javaDeveloper = new BackEndDeveloper(new WorkBackEnd());
		javaDeveloper.startWork();

		Developer javaScriptDeveloper = new FrontEndDeveloper(new WorkFrontEnd());
		javaScriptDeveloper.startWork();
	}
}
```

## 브릿지 패턴의 장단점
### 장점
- 추상적인 코드를 구체적인 코드 변경 없이도 독립적으로 확장할 수 있습니다. 
- 추상적인 코드와 구체적인 코드를 분리하여 사용할 수 있습니다. 

### 단점
- 계층 구조가 늘어나 복잡도가 증가할 수 있습니다. 