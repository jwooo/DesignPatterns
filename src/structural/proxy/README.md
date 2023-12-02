# PROXY PATTERN
## 프록시 패턴이란? 
- 대상 원본 객체를 대리하여 처리하게 함으로써 로직의 흐름을 제어하는 패턴입니다. 
- 클라이언트가 대상 객체를 직접 쓰는 것이 아니라 중간에 프록시를 거쳐서 쓰는 코드 패턴이라고 보면 됩니다. 
- 따라서 대상 객체의 메소드를 직접 실행하는 것이 아닌, 대상 객체에 접근하기 전에 프록시 객체의 메서드를 접근한 후 추가적인 로직을 처리한 뒤 접근하게 됩니다. 

하지만 그냥 객체를 이용하면 되지, 이렇게 번거롭게 중계 대리자를 통해 이용하는 방식을 취하는 이유는, 대상 클래스가 민감한 정보를 가지고 있거나 인스턴스화 하기 무겁거나 
추가 기능을 추가하고 싶은데, 원본 객체를 수정할 수 없는 상황일때를 극복하기 위해서 입니다. 

대체적으로 정리하자면 다음과 같은 효과를 누릴 수 있습니다.
1. 보안(`Secuirty`): 프록시는 클라이언트가 작업을 수행할 수 있는 권한이 있는지 확인하고 검사 결과가 긍정적인 경우에만 요청을 대상으로 전달합니다. 
2. 캐싱(`Caching`): 프록시가 내부 캐시를 유지하여 데이터가 캐시에 아직 존재하지 않는 경우에만 대상에서 작업이 실행되도록 한다. 
3. 데이터 유효성 검사(`Data validation`): 프록시가 입력을 대상으로 전달하기 전에 유효성을 검사한다. 
4. 지연 초기화(`Lazy initialization`): 대상의 생성 비용이 비싸다면 프록시는 그것을 필요로 할때까지 연기할 수 있다.
5. 원격 객체(`Remote objects`): 프록시는 원격 위치에 있는 객체를 가져와서 로컬처럼 보이게 할 수 있다. 

## 프록시 패턴 구조 
![](https://www.mscharhag.com/files/2020/proxy-pattern.jpg)

### Subject
- `Proxy`와 `RealSubject`를 하나로 묶는 인터페이스
- 대상 객체와 프록시 역할을 동일하게 하는 추상 메소드 `operation()` 메서드를 정의합니다. 
- 인터페이스가 있기 때문에 클라이언트는 `Proxy` 역할과 `RealSubject` 역할의 차이를 의식할 필요가 없습니다. 
### RealSubject
- 원본 대상 객체
### Proxy
- 대상 객체(`RealSubject`)를 중계할 대리자 역할
- 프록시는 대상 객체를 합성 합니다. 
- 프록시는 대상 객체와 같은 이름의 메서드를 호출하며, 별도의 로직을 수행할 수 있습니다. 
- 프록시는 흐름제어만 할 뿐 결과값을 조작하거나 변경시키면 안됩니다. 
### Client
- `Subject` 인터페이스를 이용하여 프록시 객체를 생성해 이용합니다. 
- 클라이언트는 프록시를 중간에 두고 프록시를 통해서 `RealSubject`와 데이터를 주고 받습니다. 

## 프록시 패턴 종류
`Proxy` 패턴은 단순하면서도 자주 쓰이는 패턴이며, 그 활용 방식도 다양합니다. 
같은 프록시 객체라도 어더한 로직을 짜느냐에 따라 그 활용도는 천차만별이 됩니다. `Proxy` 패턴의 기본형을 어떤 방식으로 변형하느냐에 따라 프록시 종류가 나뉘어지게 됩니다. 

### 기본형 프록시(Noraml Proxy)
```java
interface iSubject {
    void action();
}

class RealSubject implements ISubject {
    public void action() {
        System.out.println("원본 객체 액션");
    }
}
```
```java
class Proxy implements  ISubject {
    private RealSubject subject;
    
    Proxy(RealSubject subject) {
        this.subject = subject;
    }
    
    public void action() {
        subject.action();
        System.out.println("프록시 객체 액션");
    }
}

class Client {
    public static void main(String[] args) {
        ISubject sub = new Proxy(new RealSubject());
        sub.action();
    }
}
```

### 가상 프록시(Virtual Proxy)
- 지연 초기화 방식
- 가끔 필요하지만 항상 메모리에 적재되어 있는 무거운 서비스 객체가 있는 경우
- 이 구현은 실제 객체의 생성에 많은 자원이 소모 되지만 사용 빈도는 낮을 때 사용되는 방식입니다. 
- 서비스가 시작될 때 객체를 생성하는 대신에 객체 초기화가 실제로 필요한 시점에 초기화될 수 있도록 지연할 수 있습니다. 

```java
class Proxy implements ISubject {
    private RealSubject subject;
    
    Proxy() {
    }
    
    public void action() {
        if (subject == null) {
            subject = new RealSubject();
        }
        subject.action();
        
        System.out.println("프록시 객체 액션");
    }
}

class Client {
    public static void main(String[] args) {
        ISubject sub = new Proxy();
        sub.action();
    }
}
```

### 보호 프록시(Protection Proxy)
- 프록시가 대상 객체에 대한 자원으로의 엑세스 제어(접근 권한)
- 특정 클라이언트만 서비스 객체를 사용할 수 있도록 하는 경우
- 프록시 객체를 통해 클라이언트의 자격 증명이 기준과 일치하는 경우에만 서비스 객체에 요청을 전달할 수 있게 합니다. 

```java
class Proxy implements ISubject {
    private RealSubject subject;
    boolean access;
    
    Proxy(RealSubject subject, boolena access) {
        this.subject = subject;
        this.access = access;
    }
    
    public void action() {
        if (access) {
            subject.action();
            
            System.out.println("프록시 객체 액션");
        }
    }
}

class Client {
    public static void main(String[] args) {
        ISubject sub = new Proxy(new ReaSubmect(), false);
        sub.action();
    }
}
```

## 프록시 패턴 특징
### 프록시 패턴 사용 시기 
- 접근을 제어하거나 기능을 추가하고 싶지만, 기존의 특정 객체를 수정할 수 없는 경우
- 초기화 지연, 접근 제어, 로깅, 캐싱 등 기존 객체 동작에 수정없이 가미하고 싶을 때 

### 패턴 장점
- 개방 폐쇄 원칙을 준수 합니다. 
- 단일 책임 원칙을 준수 합니다. 
- 원래 하려던 기능을 수행하며 그외의 부가적인 작업을 수행하는데 유용합니다. 
- 클라이언트는 객체를 신경쓰지 않고, 서비스 객체를 제어하거나 생명 주기를 관리할 수 있습니다. 
- 사용자 입장에서는 프록시 객체나 실제 객체나 사용법은 유사하므로 사용성에 문제가 되지 않습니다. 

### 패턴 단점
- 많은 프록시 클래스를 도입해야 하므로 코드의 복잡도가 올라갑니다. 
- 프록시 클래스 자체에 들어가는 자원이 많다면 서비스로부터의 응답이 늦어질 수 있습니다. 

## 프록시 패턴 예제 
이미지를 보여주는 프로그램을 개발한다고 가정해볼때 프록시 패턴을 이용하여 메모리 로딩 시간을 줄일 수 있습니다. 

즉, 프록시 클래스에서 사용자가 선택한 이미지만 로드해서 렌더링하도록 대상 객체를 제어하면 되는 것입니다.
```java
interface Image {
    public void displayImage();
}
```
```java
class RealImage implements Image {
    private String fileName;
    
    public RealImage(String fileName) {
        this.fileName = fileName;
    }
    
    private void loadImageFromDisk() {
        System.out.println("Loading " + fileName);
    }
    
    @Override
    public void displayImage() {
        System.out.println("Displaying " + fileName);
    }
}

class ProxyImage implements Image {
    private String fileName;
    private Image image;
    
    public ProxyImage(String fileName) {
        this.fileName = fileName;
    }
    
    @Override
    public void displayImage() {
        if (image == null) {
            image = new RealImage(fileName);
        }
        image.displayImage();
    }
}
```
```java
class Client {
    public static void main(String[] args) {
        Image image1 = new ProxyImage("Photo1");
        Image image2 = new ProxyImage("Photo2");
        
        image1.displayImage();
        image2.displayImage();
    }
}
```
위와 같이 작성한다면 `Client`가 프록시 객체의 `displayImage()` 메서드를 호출하는 것으로 대상 객체를 생성할 수 있습니다. 