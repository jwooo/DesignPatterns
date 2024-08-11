# MEDIATOR PATTERN
## 중재자 패턴이란? 
- 중재자 패턴은 객체 간의 혼란스러운 의존 관계들을 줄일 수 있는 행동 디자인 패턴입니다. 
- 이 패턴은 객체 간의 직접 통신을 제한하고 중재자 객체를 통해서만 협력하도록 합니다. 

## 중재자 패턴 구조 
![](https://refactoring.guru/images/patterns/diagrams/mediator/structure.png?id=1f2accc7820ecfe9665b6d30cbc0bc61)
### Component
- 어떤 비즈니스 로직을 포함한 다양한 클래스들입니다. 
- 각 컴포넌트에는 중재자에 대한 참조가 있습니다. 
  - 이 중재자는 중재자 인터페이스의 유형으로 선언됩니다. 
- 중재자의 실제 클래스를 인식하지 못하므로 컴포넌트를 다른 중재자에 연결하여 다른 프로그램에서 재사용할 수 있습니다. 
### Mediator
- 일반적으로 단일 알림 메서드만을 포함하는 컴포넌트들과의 통신 메서드들을 선언합니다. 
- 컴포넌트들은 자체 객체들을 포함하여 모든 컨텍스트를 이 메서드의 인수로 전달할 수 있지만 이는 수신자 컴포넌트와 발송자 클래스 간의 결합이 발생하지 않는 방식으로만 가능합니다. 
### ConcreteMediator
- 다양한 컴포넌트 간의 관계를 캡슐화 합니다. 
- 자신이 관리하는 모든 컴포넌트에 대한 참조를 유지하고 때로는 그들의 수명주기를 관리하기도 합니다. 

## 중재자 패턴 특징
### 중재자 패턴 장점
- 단일 책임 원칙을 통해 다양한 컴포넌트 간의 통신을 한 곳으로 추출하여 코드를 이해하고 유지관리하기 쉽게 만들수 있습니다. 
- 개방/폐쇄 원칙을 통해 실제 컴포넌트들을 변경하지 않고도 새로운 중재자들을 도입할 수 있습니다. 
- 프로그램의 다양한 컴포넌트 간의 결합도를 줄일 수 있습니다. 
- 개별 컴포넌트들을 더 쉽게 재사용할 수 있습니다. 
### 중재자 패턴 단점
- 중재자는 전지전능한 객체로 발전할지도 모르기에 관리가 필요합니다. 

## 중재자 패턴 예시 
아래는 중재자 패턴을 이용하여 채팅 프로그램을 만드는 예제 입니다. 
```java
public interface Mediator {
  void addUser(User user);
  void sendMessage(User user, String message);
}
```
```java
public class UserMessageMediator {
  
  private List<User> users = new ArrayList<>();
  
  @Override
  public void addUser(User user) {
    users.add(user);
  }
  
  @Override
  public void sendMessage(User user, String message) {
    users.forEach(receiver -> {
      if (receiver != user) receiver.receive(message);
    });
  }
}
```
`Mediator` 인터페이스는 유저를 추가하고 메세지를 전송할 수 있는 메서드를 가지고 있고 구현체 클래스인 `UserMessageMediator`는 그에 맞는 구현을 하고 있습니다. 

```java
public abstract class User {
  
  protected Mediator mediator;
  
  public User(Mediator mediator) {
    this.mediator = mediator;
  }
  
  abstract void receive(String message);
  abstract void send(String message);
  
}
```
```java
public class Creator extends User {
  
  private String username;
  
  public Creator(Mediator mediator, String username) {
    super(mediator);
    this.username = username;
  }

  @Override
  void receive(String message) {
    System.out.println(username + " Got Message / " + message);
  }

  @Override
  void send(String message) {
    mediator.sendMessage(this, "[CREATOR] " + this.username + ": " + message);
  }
  
}
```
`Creator` 클래스는 `User` 추상 클래스를 상속 받고 있으며 메세지를 전송할 때 `Mediator`를 통해 메세지를 전달합니다. 
```java
public class NormalUser extends User {
  
  private String username;
  
  public NormalUser(Mediator mediator, String username) {
    super(mediator);
    this.username = username;
  }

  @Override
  void receive(String message) {
    System.out.println(username + " Got Message / " + message);
  }

  @Override
  void send(String message) {
    mediator.sendMessage(this, this.username + ": " + message);
  }
  
}
```
`NormalUser`클래스도 해당 클래스의 상황에 맞게 메서드를 오버라이딩 한 후 메세지를 전송할 때 `Mediator`를 통해 메세지를 전달합니다. 

```java
public class Client {
  
  public static void main(String[] args) {
    Mediator mediator = new UserMessageMediator();

    User creator = new Creator(mediator, "userA");
    User normalUser = new NormalUser(mediator, "userB");
    User normalUser2 = new NormalUser(mediator, "userC");

    mediator.addUser(creator);
    mediator.addUser(normalUser);
    mediator.addUser(normalUser2);

    creator.send("Hello, EveryOne!");
    normalUser.send("Hi! Creator!");

  }
  
}
```
위 코드를 실행하면 아래와 같은 결과를 얻을 수 있습니다. 
```
userB Got Message / [CREATOR] userA: Hello, EveryOne!
userC Got Message / [CREATOR] userA: Hello, EveryOne!
userA Got Message / userB: Hi! Creator!
userC Got Message / userB: Hi! Creator!
```
전송한 `User`는 메세지를 전달 받지 않고 수신을 받아야만 하는 `User`들만 `Mediator`를 통해 전달 받는 결과를 확인할 수 있습니다.