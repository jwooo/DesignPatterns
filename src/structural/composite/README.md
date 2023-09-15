# COMPOSITE PATTERN

## 복합체 패턴이란? 
복합체 패턴(Composite Pattern)은 복합 객체(Composite)와 단일 객체(Leaf)를 동일한 컴포넌트로 취급하여, 클라이언트에게 이 둘을 구분하지 않고 
동일한 인터페이스를 사용하도록 하는 구조 패턴입니다. 

![](https://upload.wikimedia.org/wikipedia/commons/thumb/5/5a/Composite_UML_class_diagram_%28fixed%29.svg/600px-Composite_UML_class_diagram_%28fixed%29.svg.png)

### Component
- `Leaf`와 `Composite`를 묶는 공통적인 상위 클래스입니다.
### Composite
- 복합 객체로서, `Leaf` 역할이나 `Composite` 역할을 넣어 관리하는 역할을 합니다.
- `Component` 구현체들을 내부리스트로 관리합니다.
- `add()`와 `remove()` 메서드는 내부 리스트에 단일/복합 객체를 저장합니다. 
- `Component` 인터페이스와 구현 메서드인 `operation()`은 복합 객체에서 호출되면 재귀하여, 추가 단일 객체를 저장한 하위 복합 객체를 순회하게 됩니다. 
### Leaf
- `Component` 인터페이스의 구현 메서드인 `operation()`은 단일 객체에서 호출되면 적절한 값만 반환한다.

복합체 패턴의 핵심은 `Composite`과 `Leaf`가 동시에 구현하는 `operation()` 인터페이스 추상 메서드를 정의하고, `Composite` 객체의 
`operation()` 메서드는 자기 자신을 호출하는 재귀 형태로 구현하는 것입니다. 

## 복합체 패턴 구성
```java
interface Component {
	void operation();
}
```

```java
class Leaf implements Component {
	
	@Override
    public void operation() {
		System.out.println(this + " 호출");
    }
}
```
```java
class Composite implements Component {
	List<Component> components = new ArrayList()<>;
	
	public void add(Component c) {
		components.add(c);
    }
	
	public void remove(Component c) {
		components.remove(c);
    }
	
	@Override
    public void operation() {
		System.out.println(this + " 호출");
		
		for (Component component : components) {
			components.operation();
		}
    }
	
	public List<Component> getChild() {
		return components;
    }
}
```
### 클래스 흐름
```java
class Client {
	public static void main() {
		// 1. 최상위 복합체 생성
		Composite composite1 = new Composite();

		// 2. 최상위 복합체에 저장한 Leaf와 또 다른 서브 복합체 생성
		Leaf leaf1 = new Leaf();
		Composite composite2 = new Composite();
		
		// 3. 최상위 복합체에 개체들을 등록 
		composite1.add(leaf1);
		composite1.add(composite2);
		
		// 4. 서브 복합체에 저장할 Leaf 생성
		Leaf leaf2 = new Leaf();
		Leaf leaf3 = new Leaf();
		Leaf leaf4 = new Leaf();
		
		// 5. 서브 복합체에 개체들을 등록
		composite2.add(leaf2);
		composite2.add(leaf3);
		composite2.add(leaf4);
		
		// 6. 최상위 복합체의 모든 자식 노드들을 출력 
		composite1.operation();
    }
}
```

## 복합체 패턴 특징 
### 패턴 사용 시기
- 데이터를 다룰 때 계층적 트리 표현을 다루어야 할때
- 복잡하고 난해한 단일/복합 객체 관계를 간편히 단순화하여 균일하게 처리하고 싶을때

### 패턴 장점
- 단일체와 복합체를 동일하게 여기기 때문에 묶어서 연산하거나 관리할 때 편리합니다.
- 다형성 재귀를 통해 복잡한 트리 구조를 보다 편리하게 구성할 수 있습니다. 
- 수평적, 수직적 모든 방향으로 객체를 확장할 수 있습니다. 
- 새로운 `Leaf` 클래스를 추가하더라도 클라이언트는 추상화된 인터페이스 만을 바라보기 때문에 개방 폐쇄 원칙(OCP)을 준수합니다. 

### 패턴 단점
- 재귀 호출 특징 상 트리의 깊이(depth)가 깊어질 수록 디버깅에 어려움이 생깁니다. 
- 설계가 지나치게 범용성을 갖기 때문에 새로운 요소를 추가할 때 복합 객체에 구성 요소에 제약을 갖기 힘듭니다. 
- 예를들어, 계층형 구조에서 `Leaf` 객체와 `Composite` 객체들을 모두 동일한 인터페이스로 다루어야하는데, 이 공통 인터페이스 설계가 까다로울 수 있다. 
  - 복합 객체가 가지는 부분 객체의 종류를 제한할 필요가 있을 때 
  - 수평적 방향으로만 확장이 가능하도록 `Leaf`를 제한하는 `Composite`를 만들때