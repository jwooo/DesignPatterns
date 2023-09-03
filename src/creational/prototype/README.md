# PROTOTYPE PATTERN

---

## 프로토타입 패턴이란? 
- 기존 인스턴스를 복제하여 새로운 인스턴스를 만드는 패턴입니다. 
- 기존 객체를 응용해서 새로운 객체를 만들 때 유용한 패턴입니다. 
  - 기존의 객체를 생성할 때 시간이 오랠걸릴 때 유용합니다.

## 프로토타입 패턴의 특징
프로토타입 패턴의 메커니즘은 매우 단순합니다. 
객체를 새롭게 생성하는데 큰 리소스가 드는 객체의 값을 모두 복제해 새로운 인스턴스를 만드는 것이 프로토타입 패턴입니다.

값을 복제하기 때문에 2가지 특성을 만족해야 합니다. 

1. `cloneInstance != instance`
   - 다른 인스턴스가 새로 만들어지기 때문에 `clone`한 객체는 기존 객체와 다른 객체여야 합니다.
2. `cloneInstance.equals(instance)`
   -값을 모두 동일하게 복제하기 때문에 equals()는 `true`이어야 합니다. 

## 프로토타입 패턴 구현
프로토 타입 패턴은, 기존의 다른 패턴과 달리 새롭게 구현하지 않고 Java에서 제공하는 `clone()` 메서드를 재정의하여 사용합니다. 

### Objects.class.clone()
- 기본적으로 Java가 제공하는 `clone()` 메서드를 이용해서 프로토타입 패턴을 구현할 수 있습니다. 
- `Object.class`에서 제공하는 `clone()`은 `protected` 접근제어자로 정의가 되어 있기 때문에 `clone`을 사용하고자 하는 객체에서 재정의해서 사용하여야 합니다.

### 적용하는 방법
- `clone()`을 사용할 객체에 `Cloneable` 인터페이스를 상속받아 `clone()` 메서드를 구현하여야 합니다.
- `clone()`은 `CloneNotSuppretedException`을 체크해주어야만 합니다.

```java
@Getter
@Setter
@EqualsAndHashCode
publci class GithubIssue implements Cloneable {
	private int id; 
	private String title;
	
	@Override
    public GithubIssue clone() {
		try {
			GithubIssue clone = (GithubIssue) super.clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
    }
}
```

### 결과
```java
public class App {
	public static void main(String[] args) {
		GithubIssue githubIssue = new GithubIssue();
		githubIssue.setId(1);
		githubIssue.setTitle("title");
		
		GithubIssue clone = githubIssue.clone();

		System.out.println("clone != githubIssue = " + (clone != githubIssue));
		System.out.println("clone.equals(githubIssue)" + clone.equals(githubIssue));
		System.out.println("clone.getClass() == githubIssue.getClass()" + (clone.getClass() == githubIssue.getClass()));
    }
}


// 결과
clone != githubIssue = true
clone.equals(githubIssue) = true
clone.getClass() == githubIssue.getClass() = true
```

### 참고
- `Object.class`의 `clone()`은 얕은 복사를 제공합니다.
- 깊은 복사를 하고 싶다면, 자바에서 제공하는 `clone()` 메서드를 그대로 사용하지 않고 새로 정의하여야 합니다. 

## 프로토 타입 패턴의 장점 및 단점
### 장점
- 복잡한 객체를 만드는 과정을 숨길 수 있습니다. 
- 기존 객체를 복제하는 과정이 새 인스턴스를 만드느 것보다 비용적인 면에서 효율적일 수도 있습니다. 
- 추상적인 타입을 리턴할 수 있습니다. 
  - `clone()` 메서드를 커스텀하게 정의할 수 있기 때문입니다. 

### 단점
- 복제한 객체를 만드는 과정자체가 복잡할 수 있습니다. 
  - 특히, 순환 참조가 있는 객체를 생성할 때 문제가 될 수 있습니다. 
