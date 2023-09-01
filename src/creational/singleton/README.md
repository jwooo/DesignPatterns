# SINGLETON PATTERN

---

## 싱글톤 패턴이란?
* 싱글턴 패턴은 인스턴스가 오직 1개만 생성되야 하는 경우에 사용되는 패턴입니다.
* 인스턴스가 1개만 생성되는 특징을 가진 싱글톤 패턴을 이용하면, 하나의 인스턴스를 메모리에 등록해서 
  여러 스레드가 동시에 해당 인스턴스를 공유하여 사용하게끔 할 수 있으므로, 요청이 많은 곳에서 사용하면 효율을 높일 수 있습니다.

### 싱글톤 생성 시 주의 사항
> 싱글톤을 만들때 `동시성(Concurrency)` 문제를 고려해서 싱글턴을 설계해야 한다!

### 싱글톤 패턴의 문제점

#### 1. 싱글톤 객체를 사용하는 객체간의 결합도가 높아진다.
- 싱글톤 객체를 사용하는 클래스는 해당 싱글톤 인스턴스에 직접적으로 의존합니다.
다른 클래스의 인스턴스를 생성하거나 메서드를 호출할 때, 해당 싱글톤 객체에 대한 참조가 필요합니다. 
이로 인해 의존성이 생기며, 결합도가 증가합니다. 
- 이로 인해 SOLID 중 `개방-폐쇄 원칙(Open-Closed Principal)`에 어긋날 가능성이 있습니다. 

#### 2. 내부 상태를 변경하기 어렵다.
- 싱글톤 객체를 수정할 경우 이를 참조하고 있는 모든 값 들이 변경되어야 하기 때문에 변경에 유연하게 대처할 수 없습니다. 

#### 3. 멀티 스레드 환경에서 동기화 문제가 발생 할 수 있습니다. 
- 싱글톤은 멀티 스레딩 환경에서도 문제 없이 동작해야 합니다. 
따라서 `Thread-safe`를 보장하려면, 무상태성을 지켜야 합니다. 
- 즉, 상태 정보를 클래스 내부에 가지고 있으면
안됩니다. 

## 자바의 싱글톤 패턴
싱글톤의 공통적인 특징은 `private constructor` 를 가진다는 것과, `static method` 를 사용한다는 점입니다. 

이러한 특징을 가지는 싱글톤을 여러 방식으로 구현할 수 있습니다. 

### 1. Eager Initialization(이른 초기화, Thread-safe)
```java
public class BasicSingleton {
	// Eager Initialization
    private static BasicSingleton instance = new BasicSingleton();
	
	private BasicSingleton() {}

    public static BasicSingleton getInstance() {
		return instance;
    }
}
```
이른 초기화 방식은, static 키워드의 특징을 이용해서 클래스 로더가 초기화 하는 시점에서 `정적 바인딩(static binding)`을 통해
해당 인스턴스를 메모리에 등록해서 사용하는 것입니다. 


이른 초기화 방식은 클래스 로더에 의해 클래스가 최초 로딩 될 때 객체가 생성되기 때문에 
`Thread-safe` 합니다. 

하지만 위와 같은 방식은 인스턴스가 사용되는 시점이 아닌 클래스 로딩 시점에 실행이 된다는 문제점을 가지고 있습니다.

### 2. Lazy Initialization with synchronized(동기화 블럭, Thread-safe)
두 번째 방식은, `synchronized` 키워드를 이용한 게으른 초기화 방식인데, 
메서드에 동기화 블럭을 지정해서 `Thread-safe`를 보장합니다.

```java
public class LazyInitSingleton {
	private static LazyInitSingleton instance;
	
	private LazyInitSingleton() {}
    
    public static synchronized LazyInitSingleton getInstance() {
		if (instance == null) {
			instance = new LazyInitSingleton();
		}
		return instance;
    }
}
```

위와 같은 방식으로 작성하면 컴파일 시점에 인스턴스를 생성하는 것이 아닌, 
인스턴스가 필요한 시점에 요청하여 `동적 바인딩(dynamic binding)` 을 통해 인스턴스를 생성하는 방식으로 사용할 수 있습니다. 

하지만 위와 같은 방식도 문제가 있습니다. 

동기화 블럭으로 지정한 게으른 초기화 방식은 `Thread-safe`하지만 `synchronized` 키워를 사용하기 때문에
성능이 떨어지는 문제가 발생합니다.

### 3. Lazy Initialization. LazyHolder(게으른 홀더, Thread-safe)
LazyHolder 방식은 `synchronized` 키워드 없이도 동시성 문제를 해결하기 때문에 좋은 성능을 가지고 있습니다. 
```java
public class LazyHolderSingleton {
	private LazyHolderSingleton() {}
    
    private static class InnerInstanceClass {
		private static final LazyHolderSingleton instance = new LazyHolderSingleton();
    }
	
	public static LazyHolderSingleton getInstance() {
		return InnerInstanceClass.instance;
    }
}
```
싱글턴 클래스에는 InnerInstanceClass 클래스의 변수가 없기 때문에, static 멤버 클래스더라도, 클래스 로더가 초기화 과정을 진행할 때
InnerInstanceClass 클래스를 초기화 하지 않고, getInstance() 메서드를 호출할 때 초기화 됩니다.

즉, `동적 바인딩(dynamic biding)`의 특징을 이용하여 `Thread-safe`하면서 좋은 성능을 가질 수 있습니다. 

InnerInstanceClass 내부 인스턴스는 static 이기 때문에 클래스 로딩 시점에 한 번만 호출 된다는 점을 
이용한 것이며, final 을 사용하여 다시 값이 할당되지 않도록 합니다.