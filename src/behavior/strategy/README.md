# STRATEGY PATTERN
## 전략 패턴이란? 
- 전략 패턴은 실행(런타임) 중에 알고리즘을 선택하여 객체 동작을 실시간으로 바뀌도록 할 수 있게 하는 행위 디자인 패턴입니다. 
- `전략`이란 일종의 알고리즘이 될 수도 있으며, 기능이나 동작이 될 수도 있는 특정한 목표를 수행하기 위한 행동 계획을 말합니다. 
  - 즉, 어떤 일을 수행하는 알고리즘이 여러가지 일때, 동작들을 미리 전략으로 정의함으로써 손쉽게 전략을 교체할 수 있는, 알고리즘 변형이 빈번하게 필요한 경우에 적합합니다. 

## 전략 패턴 구조 
![](https://www.mytechramblings.com/img/strategy-pattern-diagram.png)
### ConcreteStrategy
- 알고리즘, 행위, 동작을 객체로 정의한 구현체 

### Strategy
- 모든 전략 구현체에 대한 공용 인터페이스 

### Context
- 알고리즘을 실행해야 할 때마다 해당 알고리즘과 연결된 전략 객체의 메소드를 호출


## 전략 패턴 흐름
```java
interface Strategy {
  void doSomething();
}

class ConcreteStrategyA implements Strategy {
  public void doSomething() {
    System.out.println("execute ConcreteStrategyA");
  }
}

class ConcreteStrategyB implements Strategy {
  public void doSomething() {
    System.out.println("execute ConcreteStrategyB");
  }
}
```
```java
class Context {
  Strategy strategy; 
  
  void setStrategy(Strategy strategy) {
    this.strategy = strategy;
  }
  
  void doSomething() {
    this.strategy.doSomething();
  }
}
```
```java
class Client {
  public static void main(String[] args) {
    Context context = new Context();
    
    c.setStrategy(new ConcreteStrategyA());
    c.doSomething();
    
    c.setStrategy(new ConcreteStrategyB());
    c.doSomething();
  }
}
```

## 전략 패턴 특징 
### 사용 시기 
- 전략 알고리즘의 여러 버전 또는 변형이 필요할 때 클래스화를 통해 관리 
- 알고리즘 코드가 노출되어서는 안되는 데이터에 엑세스하거나 데이터를 활용할때 
- 알고리즘의 동작이 런타임에 실시간으로 교체 되어야 할때 

### 전략 패턴 주의점 
1. 알고리즘이 많아질수록 관리해야할 객체의 수가 늘어답니다. 
2. 만일 애플리케이션 특성이 알고리즘이 많아지고 자주 변경되지 않는다면, 새로운 클래스와 인터페이스를 만들어 프로그램을 복잡하게 만들 이유가 없습니다. 
3. 개발자는 적절한 전략을 선택하기 위해 전략 간의 차이점을 파악하고 있어야 합니다. 해당 이유로 인해 코드의 복잡도가 상승하게 됩니다. 

## 전략 패턴 예제 
### RPG 게임에서 캐릭터의 무기 전략
이 패턴 구현 예제의 컨셉은 적의 특성에 따라 주인공이 무기 전략을 바꿔가며 대응하는 것입니다. 

### 전략 패턴을 적용하지 않은 코드 
아래 코드를 살펴보면 `state` 매개변수의 값에 따라서 간접적으로 `attack()` 함수의 동작을 제어하도록 되어 있습니다. 
적이 오면 상수를 메서드에 넘겨 조건문으로 일일히 필터링하여 적절한 전략을 실행하였습니다. 

하지만 상태 변수를 통해 행위를 분기문으로 나누는 행위는 좋지 않은 방식의 코드입니다. 자칫 잘못하면 `if-else` 지옥에 빠질 수 있습니다. 

```java
class TakeWeapon {
  public static final int SWORD = 0;
  public static final int SHIELD = 1;
  public static final int CROSSBOW = 2;
  
  private int state;
  
  public void setWeapon(int state) {
    this.state = state;
  }
  
  public void attack() {
    if (state == SWORD) {
      System.out.println("칼을 휘두릅니다.");
    } else if (state == SHIELD) {
      System.out.println("방패로 막아섭니다.");
    } else if (state == CROSSBOW) {
      System.out.println("석궁을 발사합니다.");
    }
  }
}
```

```java
import behavior.strategy.wrong_example.TakeWeapon;

class User {
  public static void main(String[] args) {
    TakeWeapon hand = new TakeWeapon();
    
    hand.setWeapon(TakeWeapon.SWORD);
    hand.attack();
    
    hand.setWeapon(TakeWeapon.SHIELD);
    hand.attack();
  }
}
```

### 전략 패턴을 적용한 코드
위의 클린하지 않은 코드를 해결하는 가장 좋은 방법은 변경시키고자 하는 행위(전략)를 직접 넘겨주는 것입니다. 

우선 여러 무기들을 객체 구현체로 정의하고 이들을 `Weapon`이라는 인터페이스로 묶어줍니다. 그리고 인터페이스를 컨텍스트 클래스에 합성 시키고, `setWeapon()` 메서드를
통해 전략 인터페이스 객체의 상태를 바로바로 변경할 수 있도록 구성해주면 됩니다. 

```java
interface Weapon {
  void offensive();
}

class Sword implements Weapon {
  @Override
  public void offensive() {
    System.out.println("칼을 휘두릅니다.");
  }
}

class Shield implements Weapon {
  @Override
  public void offensive() {
    System.out.println("방패로 밀칩니다.");
  }
}

class CrossBow implements Weapon {
  @Override
  public void offensive() {
    System.out.println("석궁을 발사합니다.");
  }
}
```

```java
class TakeWeaponStrategy {
  Weapon wp;
  
  void setWeapon(Weapon wp) {
    this.wp = wp;
  }
  
  void attack() {
    wp.offensive();
  }
}
```

```java
class User {
  public static void main(String[] args) {
    TakeWeaponStrategy hand = new TakeWeaponStrategy();
    
    hand.setWeapon(new Sword());
    hand.attack();
    
    hand.setWeapon(new Sheild());
    hand.attack();
    
    hand.setWeapon(new CrossBow());
    hand.attack();
  }
}
```
