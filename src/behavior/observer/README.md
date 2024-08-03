# OBSERVER PATTERN
## 옵저버 패턴이란? 
- 옵저버(관찰자)들이 관찰하고 있는 대상자의 상태가 변화가 있을 때마다 대상자는 직접 목록의 각 관찰자들에게 통지하고, 관찰자들은 알림을 받아 조치를 취하는 행동 패턴
- 다른 디자인 패턴과 다르게 `One-To-Many` 의존성을 가지는데, 주로 분산 이벤트 핸들링 시스템을 구현하는데 사용

## 옵저버 패턴 구조 
![](https://www.programmergirl.com/wp-content/uploads/2019/08/TheObserverPattern.png)

### Subject
- 관찰 대상자를 정의하는 인터페이스 
```java
public interface Subject {
    void register(Observer observer);
    void unregister(Observer observer);
    void notifyAll();
}
```

### SubjectImpl
- 관찰 당하는 대상자/발행자/게시자
- `Observer`들을 리스트(`List`, `Map`, `Set`.. 등)로 모아 합성(`Composition`)하여 가지고 있음 
- `Subject`의 역할은 관리자인 `Observer`들을 내부 리스트에 등록/삭제하는 인프라를 가지고 있다. 
- `Subject`가 상태를 변경하거나 어떤 동작을 실행할 때, `Observer`들에게 이벤트 알림(`notify`)을 발행한다. 
```java
public class ConcreteSubject implements Subject {
    
    private List<Observer> observers = new ArrayList<>();
    
    @Override
    public void register(Observer observer) {
        observers.add(observer);
    }
    
    @Override
    public void unregister(Observer observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyAll() {
        for (Observer o : observers) {
            o.update();
        }
    }
    
}
```

### Observer
- 구독자들을 묶는 인터페이스 
```java
public interface Observer {
    void update();
}
```

### ConcreteObserver
- `ConcreteObserver`들은 `Subject`가 발행한 알림에 대해 현재 상태를 취득한다. 
- `Subject`의 업데이트에 대해 전후 정보를 처리한다. 
```java
public class ConcreteObserverA implements Observer {
    
    @Override
    public void update() {
        System.out.println("ConcreteObserverA Event");
    }
    
}

public class ConcreteObserverB implements Observer {
    
    @Override
    public void update() {
        System.out.println("ConcreteObserverB Event");
    }
    
}
```

## 옵저버 패턴 특징 
### 패턴 사용 시기 
- 앱이 한정된 시간, 특정한 케이스에만 다른 객체를 관찰해야 하는 경우 
- 대상 객체의 상태가 변경될 때 마다 다른 객체의 동작을 트리거해야 할 때 
- 한 객체의 상태가 변경되면 다른 객체도 변경해야 할 때

### 패턴 장점
- `Subject`의 상태 변경을 주기적으로 조회하지 않고 자동으로 감지할 수 있다. 
- 발행자의 코드를 변경하지 않고도 새 구독자 클래스를 도입할 수 있어 개방 폐쇄 원칙을 준수한다. 
- 런타임 시점에서 발행자와 구독 알림 관계를 맺을 수 있다. 
- 상태를 변경하는 객체(`Subject`)와 변경을 감지하는 객체(`Observer`)의 관계를 느슨하게 유지할 수 있다.

### 패턴 단점
- 구독자는 알림 순서를 제어할 수 없고, 무작위 순서로 알림을 받음
- 옵저버 패턴을 자주 구성하면 구조와 동작을 알아보기 힘들어져 코드 복잡도가 증가한다. 
- 다수의 옵저버 객체를 등록 이후 해지하지 않는다면 메모리 누수가 발생할 수도 있다. 

## 옵저버 패턴 예제 
현재 날씨의 온도, 습도, 기압 데이터를 얻을 수 있는 서비스인 `WeatherAPI` 클래스가 있다고 하자.

해당 프로그램의 요구사항은 사용자들이 이 날씨 `API`를 이용해 온습도 데이터를 가져와 `display` 한다는 것이다. 
해당 요구사항을 `Observer` 패턴으로 구현하려면 날씨 `API`를 신청한 유저들을 관리하는 프로세스를 클라이언트가 아닌 날씨 `API` 클래스에서 관리하도록 설정하고, 
각 유저들이 변화된 날씨 데이터를 자동으로 전달받을 수 있도록 일종의 전파 및 동작 행위를 날씨 `API` 클래스에서 구현할 필요성이 있다. 

즉, `WeatherAPI`를 발행자, 관찰 대상자로서 `Subject` 인터페이스를 구현하도록 하고, `API` 사용자들을 관찰자 구독자로서 `Observer` 인터페이스를 구현하도록 구성한다. 


```java
public interface Subject {
    void register(Observer o);
    void unregister(Observer o);
    void notifyObservers();
}
```

```java
public class WeatherAPI implements Subject {
    
    float temp;
    float humidity;
    float pressure;
    List<Observer> subscribers = new ArrayList<>();
    
    @Override
    public void register(Observer o) {
        subscribers.add(o);
    }
    
    @Override
    public void unregister(Observer o) {
        subscribers.remove(o);
    }
    
    @Override
    public void notifyObservers() {
        for (Observer o : subscribers) {
            o.display(this);
        }
    }
    
    public void measurementsChanged() {
        temp = new Random().nextFloat() * 100;
        humidity = new Random().nextFloat() * 100;
        pressure = new Random().nextFloat() * 100;
        
        notifyObservers();
    }
    
}
```

```java
interface Observer {
    void display(WeatherAPI api);
}

class KoreanUser implements Observer {
    String name;

    KoreanUser(String name) {
        this.name = name;
    }

    public void display(WeatherAPI api) {
        System.out.printf("%s님이 현재 날씨 상태를 조회함 : %.2f°C %.2fg/m3 %.2fhPa\n", name, api.temp, api.humidity, api.pressure);
    }
}
```

```java
public class Client {
    public static void main(String[] args) {
        WeatherAPI api = new WeatherAPI();

        api.registerObserver(new KoreanUser("홍길동"));
        api.registerObserver(new KoreanUser("임꺽정"));
        api.registerObserver(new KoreanUser("세종대왕"));

        // 온습도기에서 현재 상태의 온습도 정보가 갱신됨
        api.measurementsChanged();

        // 알아서 전파되어 출력

        api.measurementsChanged();

        // ...
    }
}
```

코드를 위와 같이 구성하면 클라이언트에서 별 다른 명령없이 알아서 자동으로 발행자로부터 구독자들이 데이터를 전달받아 적절히 자동으로 처리하는 것을 볼 수 있다. 
핵심은 구독자들의 관리를 발행자 객체에서 리스트로 한다는 점과 리스트에 있는 모든 구독자들의 메서드를 위임하여 실행함으로써 변화가 있을때마다 이벤트성 알림으로 전파한다는 컨셉으로 옵저버 패턴을 이해하면 된다. 



