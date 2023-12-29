# STATE PATTERN
## 상태 패턴이란? 
상태패턴은 객체가 특정 상태에 따라 행위를 달리하는 상황에서, 상태를 조건문으로 검사해서 행위를 달리하는 것이 아닌, 
상태를 객체화하여 상태가 행동을 할 수 있도록 위임하는 패턴을 말합니다. 

객체 지향 프로그래밍에서의 클래스는 꼭 사물/생물만을 표현하는 고체 형태의 데이터만 표현할 수 있는것이 아닙니다. 
경우에 따라서 무형태의 행위/동작도 클래스로 묶어 표현할 수도 있습니다. 

그래서 상태를 클래스로 표현하면 클래스를 교채해서 '상태의 변화'를 표현할 수 있고, 객체 내부 상태 변경에 따라 객체의 행동을 
상태에 특화된 행동들로 분리해낼 수 있으며, 새로운 행동을 추가하더라도 다른 행동에 영향을 주지 않습니다. 

## 상태 패턴 구조
![](https://reactiveprogramming.io/_next/image?url=%2Fbooks%2Fpatterns%2Fimg%2Fpatterns-articles%2Fstate-diagram.png&w=3840&q=75)
### AbstractState
- 상태를 추상화한 고수준 모듈

### ConcreteState
- 구체적인 각각의 상태를 클래스로 표현
- `AbstractState` 역할로 결정되는 인터페이스를 구체적으로 구현한다.
- 다음 상태가 결정되면 `Context`에 상태 변경을 요청하는 역할을 한다. 

### Context
- `State`를 이용하는 시스템
- 시스템 상태를 나타내는 `State` 객체를 합성하여 가지고 있다. 
- 클라이언트로부터 요청받은 `State`객체에 행위 실행을 위임한다. 

## 상태 패턴 특징
### 사용 시기 
- 객체의 행동이 상태에 따라 각기 다른 동작을 할 때 
- 상태 및 전환에 걸쳐 대규모 조건 분기 코드와 중복 코드가 많을 경우 
- 조건문의 각 분기를 별도의 클래스에 넣는 것이 상태 패턴의 핵심
- 런타임단에서 객체의 상태를 유동적으로 변경해야 할 때 

### 패턴 장점
- 상태에 따른 동작을 개별 클래스로 옮겨서 관리할 수 있다. 
- 상태와 관련된 모든 동작을 각각의 상태 클래스에 분산시킴으로써, 코드의 복잡도를 줄일 수 있다. 
- 단일 책임 원칙과 개방 폐쇄 원칙을 준수 할 수 있다. 
- 하나의 상태 객체만 사용하여 상태 변경을 하므로 일관성 없는 상태 주입을 방지하는데 도움이 된다. 

### 패턴 단점
- 상태 별로 클래스를 생성하므로, 관리해야할 클래스 수 증가
- 상태 클래스 갯수가 많고 규칙이 자주 변경된다면, `Context`의 상태 변경 코드가 복잡해지게 될 수 있다. 
- 객체에 적용할 상태가 몇가지 밖에 없거나 거의 상태 변경이 이루어지지 않는 경우 패턴을 적용하는 것이 과도할 수 있다. 

## 상태 패턴 예제 
### 노트북 전원 상태에 따른 동작 설계 
노트북을 켜고 끄는 상황을 생각해봅시다.
노트북에서 전원 버튼을 누르게 되면 나타나는 상태 변화는 다음과 같이 3단계로 이루어집니다. 
1. 노트북 전원이 `ON` 상태에서 전원 버튼을 누르면 노트북이 전원 `OFF` 상태로 변경
2. 노트북 전원이 `OFF` 상태에서 전원 버튼을 누르면 노트북이 전원 `ON` 상태로 변경
3. 노트북 전원 절전 모드 상태에서 전원 버튼을 누르면 노트북이 전원 `ON` 상태로 변경

### 클린하지 않은 문제의 코드 
```java
class Laptop {
    public static final int OFF = 0;
    public static final int SAVING = 1;
    public static final int ON = 2;
    
    private int powerState;
    
    Laptop() {
        this.powerState = Laptop.OFF;
    }
    
    void changeState(int state) {
        this.powerState = state;
    }
    
    void powerButtonPush() {
        if (powerState == Laptop.OFF) {
            System.out.println("전원 ON");
            changeState(Laptop.ON);
        } else if (powerState == Laptop.ON) {
            System.out.println("전원 OFF");
            changeState(Laptop.OFF);
        } else if (powerState == Laptop.SAVING) {
            System.out.println("전원 ON");
            changeState(Laptop.ON);
        }
    }
    
    void setSavingState() {
        System.out.println("절전 모드");
        changeState(Laptop.SAVING);
    }
    
    void typeButtonPush() {
        if (powerState == Laptop.OFF) {
            throw new IllegalArgumentException("노트북이 OFF 인 상태");
        } else if (powerState == Laptop.ON) {
            System.out.println("키 입력");
        } else if (powerState == Laptop.SAVING) {
            throw new IllegalArgumentException("노트북이 절전모드 인 상태");
        }
    }
    
    void currentStatePrint() {
        if (powerState == Laptop.OFF) {
            System.out.println("노트북이 전원 OFF 인 상태");
        } else if (powerState == Laptop.ON) {
            System.out.println("노트북이 전원 ON 인 상태");
        } else if (powerState == Laptop.SAVING) {
            System.out.println("노트북이 절전모드 인 상태");
        }
    }
}
```
```java
class Client {
    public static void main(String[] args) {
        Laptop laptop = new Laptop();
        laptop.currentStatePrint();

        laptop.powerButtonPush();
        laptop.currentStatePrint();
        laptop.typebuttonPush();

        laptop.setSavingState();
        laptop.currentStatePrint();

        laptop.powerButtonPush();
        laptop.currentStatePrint();

        laptop.powerButtonPush();
        laptop.currentStatePrint();
    }
}
```

### 상태 패턴을 적용한 코드 
상태 패턴의 핵심은 상태를 객체화 하는 것입니다. 

노트북의 상태 3가지를 모두 클래스로 구성합니다. 그리고 인터페이스나 추상 클래스로 묶어 추상화/캡슐화를 합니다. 
상태를 클래스로 분리하였으니, 상태에 따른 행동 메서드도 각 상태 클래스마다 구현을 해줍니다. 

결과적으로 코드의 전체 라인 수가 길어지고 괜히 클래스가 많아져 읽기 힘들것 같지만, 오히려 이런 방법이 나중에 유지보수를 용이하게 해줍니다. 
```java
interface PowerState {
    void powerButtonPush(LaptopContext context);
    void typeButtonPush();
}

public class OnState implements PowerState {
    @Override
    public void powerButtonPush(LaptopContext context) {
        System.out.println("노트북 전원 OFF");
        context.changeState(new OffState());
    }

    @Override
    public void typeButtonPush() {
        System.out.println("키 입력");
    }

    @Override
    public String toString() {
        return "노트북이 전원 ON인 상태입니다.";
    }
}

public class OffState implements PowerState {
    @Override
    public void powerButtonPush(LaptopContext context) {
        System.out.println("노트북 전원 ON");
        context.changeState(new OnState());
    }

    @Override
    public void typeButtonPush() {
        throw new IllegalArgumentException("노트북이 전원 OFF인 상태");
    }

    @Override
    public String toString() {
        return "노트북이 전원 OFF인 상태입니다.";
    }
}


public class SavingState implements PowerState {
    @Override
    public void powerButtonPush(LaptopContext context) {
        System.out.println("노트북 전원 ON");
        context.changeState(new OnState());
    }

    @Override
    public void typeButtonPush() {
        throw new IllegalArgumentException("노트북이 절전 모드인 상태입니다.");
    }

    @Override
    public String toString() {
        return "노트북이 절전모드인 상태입니다.";
    }
}
```
```java
public class LaptopContext {
    PowerState powerState;

    LaptopContext() {
        this.powerState = new OffState();
    }

    void changeState(PowerState state) {
        this.powerState = state;
    }

    void setSavingState() {
        System.out.println("노트북 절전 모드");
        changeState(new SavingState());
    }

    void powerButtonPush() {
        powerState.powerButtonPush(this);
    }

    void typeButtonPush() {
        powerState.typeButtonPush();
    }

    void currentStatePrint() {
        System.out.println(powerState.toString());
    }
}
```
```java
class Client {
    public static void main(String[] args) {
        public class Client {
            public static void main(String[] args) {
                LaptopContext laptop = new LaptopContext();
                laptop.currentStatePrint();

                laptop.powerButtonPush();
                laptop.currentStatePrint();
                laptop.typeButtonPush();

                laptop.setSavingState();
                laptop.currentStatePrint();

                laptop.powerButtonPush();
                laptop.currentStatePrint();

                laptop.powerButtonPush();
                laptop.currentStatePrint();
            }
        }
    }
}
```