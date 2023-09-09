# ADAPTER PATTERN

---

- 어댑터 패턴이란 이름 그대로 클래스를 어탭터로서 사용되는 패턴입니다.
- 호환성이 없는 인터페이스 때문에 함께 동작할 수 없는 클래스들을 함께 작동해주도록 변환하는 역할을 해주는 행동패턴입니다. 

## 어댑터 패턴 구조 

어댑터 패턴에는 기존 시스템의 클래스를 상속해서 호환 작업을 해주냐, 합성해서 호환 작업을 해주냐에 따라, 두 가지 패턴 방법으로 나뉘게 됩니다. 

### 객체 어댑터 
- 합성된 멤버에게 위임을 이용한 어댑터 패턴
- 자기가 해야 할 일을 클래스 멤버객체의 메서드에게 다시 시킴으로써 목적을 달성하는 것을 위임이라고 한다.
- 합성을 활용했기 때문에 런타임 중에 `Adaptee`가 결정되어 유연합니다. 
- 그러나 `Adaptee` 객체를 필드 변수로 저장해야 되기 때문에 공간 차지 비용이 듭니다. 

```java
// Adaptee: 클라이언트에서 사용하고 싶은 기존의 서비스, 호환 불가인 상태
class Service {
	void specificationMethod(int specialData) {
		System.out.println("기존 서비스 기능 호출 " + specialData);
    }
}
```
```java
// Client Interface: 클라이언트가 접근해서 사용할 고수준의 어탭터 모듈
interface Target {
	void method(int data);
}
```
```java
// Adapter: Adaptee 서비스를 클라이언트에서 사용하게 할 수 있도록 호환 처리 해주는 어댑터
class Adapter implements Target {
	Service adaptee;
	
	Adapter(Service adaptee) {
		this.adaptee = adaptee;
    }
	
	// 어댑터의 메서드가 호출되면, Adaptee의 메서드를 호출하도록 한다.
	public void method(int data) {
		adaptee.specificationMethod(data); 
		}
    }
```

```java
class Client {
	public static void main(String[] args) {
		// 1. 어뎁터 생성
		Target adapter = new Adapter(new Service());
		
		// 2. Client Interface의 스펙에 따라 메서드를 실행하면 기존 서비스의 메서드가 실행된다.
		adapter.method(1);
    }
}
```

### 클래스 어댑터 
- 클래스 상속을 이용한 어댑터 패턴입니다.
- `Adaptee`를 상속했기 대문에 따로 객체 구현 없이 바로 코드 재사용이 가능합니다.
- 상속은 대표적으로 기존에 구현된 코드를 재사용하는 방식이지만, 자바에서는 다중 상속 불가 문제 때문에 전반적으로 권장하지는 않습니다. 

```java
// Adaptee: 클라이언트에서 사용하고 싶은 기존의 서비스, 호환 불가
class Service {
	void specificMethod(int specialData) {
		System.out.println("기존 서비스 기능 호출 " + specialData);
    }
}
```
```java
// Client Integerface: 클라이언트가 접근해서 사용할 고수준의 어댑터 모듈
interface Target {
	void method(int data);
}
```

```java
// Adapter: Adaptee 서비스를 클라이언트에서 사용하게 할 수 있도록 호환 처리 해주는 어댑터 
class Adapter extends Service implements Target {
	
	// 어댑터의 메서드가 호출되면, 부모 클래스의 Adaptee의 메서드를 호출
	public void method(int data) {
		specificationMethod(data);
    }
}
```
```java
class Client {
	public static void main(String[] args) {
		// 1. 어댑터 생성
		Target adapter = new Adapter();
		
		// 2. 인터페이스의 스펙에 따라 메서드를 실행하면 기존 서비스의 메서드가 실행된다.
		adapter.method(1);
    }
}
```

## 어댑터 패턴 특징 
### 패턴 사용 시기
- 레거시 코드를 사용하고 싶지만 새로운 인터페이스가 레거시 코드와 호환되지 않을 때
- 이미 만든 것을 재사용하고자 하나 이 재사용 가능한 라이브러리를 수정할 수 없을 때
- 이미 만들어진 클래스를 새로운 인터페이스에 맞게 개조할 때 
- 소프트웨어의 구 버전과 신 버전을 공존시키고 싶을 때

### 패턴 장점
- 프로그램의 기본 비즈니스 로직에서 인터페이스 또는 데이터 변환 코드를 분리할 수 있기 때문에 단일 책임 원칙(SRP)를 만족합니다. 
- 기존 클래스 코드를 건들지 않고 클라이언트 인터페이스를 통해 어댑터와 작동하기 때문에 개방 폐쇄 원칙(OCP)를 만족합니다. 
- 만일 추가로 필요한 메서드가 있다면 어댑터에 빠르게 만들 수 있습니다. 만약 버그가 발생해도 기존의 클래스에는 버그가 없으므로 Adapter 역할의 클래스를 중점으로 조사하면 되고, 프로그램 검사도 쉬워집니다. 

### 패턴 단점
- 새로운 인터페이스와 어댑터 클래스 세트를 도입해야 하기 때문에 코드의 복잡성이 증가합니다. 
- 때로는 직접 `Adaptee` 클래스를 변경하는 것이 간단할 수 있는 경우가 있기 때문에 신중히 선택하여야 합니다. 
