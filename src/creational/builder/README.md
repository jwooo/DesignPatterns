# BUILDER PATTERN

## 빌더 패턴이란? 

- 복잡한 객체 생성 과정과 표현 방법을 분리하여 다양한 구성의 인스턴스를 만드는 생성 패턴입니다. 
- 생성자에 들어갈 매개 변수를 메서드로 하나하나 받아들이고 마지막에 통합하여 객체를 생성하는 방식입니다. 

## 왜 빌더 패턴을 사용해야 하나요? 

```java
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private int age;
	private int height;
	private int weight;
	
	private String username;
	private String mbti;
	
}
```
예를 들어 위의 User 클래스를 생성하는데 `mbti` 파라미터는 필수가 아니라고 가정해보면 아래와 같은 방식들로 
객체 초기화를 진행하여야 합니다. 

```java
User user = new User(20, 180, 70, "user", null);
```

```java
User user = new User();

user.setAge(20);
user.setHeight(180);
user.set(70);
user.setUsername("user");
```
하지만 위 두가지 방식은 문제점을 가지고 있습니다. 

### 1. 일관성
위 코드에서는 `mbti`를 제외한 모든 인스턴스 변수는 객체가 초기화 될때 설정되어야 합니다. 
하지만 개발자가 깜빡하고 `setAge()`나 `setHeight()`와 같은 메서드들을 호출하지 않았다면 이 객체는 일관성이 무너진 
상태가 됩니다. 즉, 객체가 유효하지 않은 것입니다. 

이는 객체를 생성하는 부분과 값을 설정하는 부분이 물리적으로 떨어져 있기 때문에 발생하는 문제점입니다. 

### 2. 불변성
`setter` 메서드는 객체를 처음 생성할 때 필드값을 설정하기 위해 존재하는 메서드입니다. 
하지만 위의 코드에서는 객체를 생성했음에도 여전히 외부적으로 `setter` 메서드를 노출하고 있으므로,
협업 과정에서 언제 어디서 누군가 `setter` 메서드를 호출하여 함부로 객체를 조작할 수 있습니다. 

이것을 불변함을 보장할 수 없다고 이야기 합니다. 

## 빌더 패턴 구현 

아래와 같은 클래스를 빌더 패턴으로 만들려고 합니다. 
```java
class Student {
	private int id;
	private String name;
	private String grade;
	private String phoneNumber;
	
	public Student(int id, String name, String grade, String phoneNumber) {
		this.id = id;
		this.name = name;
		this.grade = grade;
		this.phoneNumber = phoneNumber;
    }
}
```

먼저 Builder 클래스를 만들고 필드 멤버를 구성하고자 하는 Student 클래스 멤버 구성과 똑같이 구성합니다. 
```java
class StudentBuilder {
	private int id;
	private String name;
	private String grade;
	private String phoneNumber;
}
```

그리고 각 멤버에 대한 Setter 메서드를 구현해줍니다. 이때 가독성을 좋게 하면서도 기존의 Setter와 다른 특성을
가지고 있는 점을 알리기 위해서, set 단어는 빼주고 멤버 이름으로만 메서드명을 작성해줍니다. 
```java
class StudentBuilder {
	private int id;
	private String name;
	private String grade;
	private String phoneNumber;
	
	public StudentBuilder id(int id) {
		this.id = id;
		return this;
    }
	
	public StudentBuilder name(String name) {
		this.name = name;
		return this;
    }
	
	public StudentBuilder grade(String grade) {
		this.grade = grade;
		return this;
    }
	
	public StudentBuilder phoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
    }
}
```
여기서 주목할 부분은 각 Setter 함수가 마지막 반환 구문인 `return this` 부분입니다. 

`this`를 리턴해줌으로써 메서드 호출 후 연속적으로 빌더 메서드를 메서드 체이닝 기법을 이용하여 호출할 수 있게 됩니다. 
```java
// Example
new StudentBuilder().id().name().grade().phoneNumber()
```

마지막으로 빌더의 최종 목표인 Student 객체를 만들어 주는 build 메서드를 구성하여 줍니다. 
빌더 클래스의 필드들을 Student 생성자 인자에 넣어줌으로써 멤버 구성이 완료된 Student 인스턴스를 얻게 됩니다. 

```java
class StudentBuilder {
	private int id;
	private String name;
	private String grade;
	private String phoneNumber;
	
	public StudentBuilder id(int id) { ... }

	public StudentBuilder name(String name) { ... }
    
	public StudentBuilder grade(String grade) { ... }
    
	public StudentBuilder phoneNumber(String phoneNumber) { ... }
    
    public Student build() {
		return new Student(id, name, grade, phoneNumber);
    }
}
```
위와 같이 구성된 Builder 클래스를 아래의 예시처럼 사용할 수 있습니다. 
```java
Student student = new StudentBuilder() 
    .id(1)
    .name("user")
    .grade("Senior")
    .phoneNumber("000-0000-0000")
    .build();
```

## 빌더 패턴 장점

### 1. 객체 생성 과정을 일관된 프로세스로 표현
생성자 방식으로 객체를 생성하는 경우 매개변수가 많아질수록 가독성이 급격하게 떨어집니다. 
반면 빌더 패턴을 적용하면 직관적으로 어떤 데이터에 어떤 값이 설정되는지 한눈에 파악할 수 있습니다. 

```java
Student Student = new Student(1, "user", "Senior", "000-0000-0000");

Student student = new StudentBuilder()
    .id(1)
    .name("user")
    .grade("Senior")
    .phoneNumber("000-0000-0000")
    .build();
```
### 2. 필수 멤버와 선택적 멤버를 분리 가능
객체 인스턴스는 목적에 따라 초기화가 필수인 멤버 변수가 있고 선택적인 멤버 변수가 있을 수 있습니다. 

만일 Student 클래스의 id 필드가 인스턴스화 할때 반드시 필수적으로 값을 지정해 주어야 하는 필수 멤버 변수라고 하면 
이를 빌더 클래스를 통해 초기화가 필수인 멤버는 빌더의 생성자로 받게 하여 필수 멤버를 설정해주어야 빌더 객체가 생성되도록
유도하고, 선택적인 멤버는 빌더의 메서드로 받도록 하면, 사용자로 하여금 필수 멤버와 선택적 멤버를 구분하여 객체 생성을 유도할 수 있습니다. 

```java
class StudentBuilder {
	private int id;
	
	private String name;
	private String grade;
	private String phoneNumber;
	
	public StudentBuilder(int id) {
		this.id = id;
	}
	
	public StudentBuilder name(String name) { ... }

	public StudentBuilder grade(String grade) { ... }
    
	public StudentBuilder phoneNumber(String phoneNumber) { ... }
    
    public Student build() {
		return new Student(id, name, grade, phoneNumber);
    }
}
```
```java
Student student1 = new StudentBuilder(1)
    .name("user")
    .grade("Senior")
    .phoneNumber("000-0000-0000")
    .build();
```

### 3. 멤버에 대한 변경 가능성 최소화를 추구
많은 개발자들이 자바 프로그래밍을 하면서 멤버에 값을 할당할 때 흔히 사용하는 것이 Setter 메서드 인데, 
그중 클래스 멤버 초기화를 Setter를 통해 구성하는 것은 매우 좋지 않은 방법입니다. 

일반적으로 프로그램을 개발하는데 있어서 다른 사람과 협업할때 가장 중요시되는 점 중 하나가 바로 불변 객체 입니다. 
불변 객체란 객체 생성 이후 내부의 상태가 변하지 않는 객체입니다. 이는 오로지 읽기 메소드만을 제공하며 쓰기는 제공하지 않습니다. 

현업에서 불변 객체를 이용해 개발해야 하는 이유로는 다음과 같습니다. 
1. 불변 객체는 Thread-Safe 하여 동기화를 고려하지 않아도 된다.
2. 만일 가변 객체를 통해 작업하는 도중 예외가 발생하면 해당 객체가 불안정한 상태에 빠질 수 있어 또 다른 에러를 유발할 수 있는 위험성이 있기 때문이다.
3. 불변 객체로 구성하면 다른 사람이 개발한 함수를 위험없이 이용을 보장할 수 있어 협업 및 유지보수에 도움이 된다.

따라서 클래스들은 가변적이어야 하는 매우 타당한 이유가 있지 않는 한 반드시 불변으로 설정해야 하며 만약 클래스를 불변으로 만드는 것이 불가능하다면
가능한 변경 가능성을 최소화 해야 합니다. 

## 빌더 패턴 단점

### 코드 복잡성 증가
빌더 패턴을 적용하려면 N개의 클래스에 대해 N개의 새로운 빌더 클래스를 만들어야 해서, 클래스 수가 기하급수적으로 늘어나
관리해야 할 클래스가 많아지고 구조가 복잡해질 수 있습니다. 
또한 선택적 매개변수를 많이 받는 객체를 생성하기 위해서는 먼저 빌더 클래스부터 정의해야 합니다. 
### 생성자 보다는 성능이 떨어진다.
매번 메서드를 호출하여 빌더를 거쳐 인스턴스화 하기 때문에 어쩌면 당연하지만 애플리케이션의 성능을 극으로 중요시되는 상황이라면 문제가 될 수 있습니다.
### 지나친 빌더를 남용해서는 안된다.
클래스의 필드의 개수가 4개보다 적고, 필드의 변경 가능성이 없는 경우라면 차라리 생성자나 정적 팩토리 메서드를 이용하는 것이 더 좋을 수 있습니다. 
빌더 패턴의 코드가 다소 장황하기 때문에 클래스의 필드의 갯수와 필드 변경 가능성을 중점으로 보고 패턴의 적용 유무를 가려야 합니다. 

다만 API는 시간이 지날수록 많은 매개변수를 갖는 경향이 있기 때문에 애초에 빌더 패턴으로 시작하는 편이 나을 때가 많다고 말하는 경향이 있습니다. 


## 한줄 정리
> 빌더 패턴은 생성자 없이 어느 객체에 대해 '변경 가능성을 최소화'를 추구하여 불변성을 갖게 해주게 되는 것이다
