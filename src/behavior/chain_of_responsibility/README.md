# CHAIN OF RESPONSIBILITY PATTERN
## 책임 연쇄 패턴이란? 
책임 연쇄 패턴은 클라이언트의 요청에 대한 세세한 처리를 하나의 객체가 몽땅 처리하는 것이 아닌, 여러개의 처리 객체 들로 나누고, 이들을 사슬 처럼 연결해 집합 안에서 
연쇄적으로 처리하는 행동 패턴입니다. 

이러한 처리 객체들을 핸들러라고 부르는데, 요청을 받으면 각 핸들러는 요청을 처리할 수 있는지, 없으면 체인의 다음 핸들러로 처리에 대한 책임을 전가합니다. 
한마디로 책임 연쇄라는 말은 요청에 대한 책임을 다른 객체에 떠넘긴다는 소리입니다. 

## 책임 연쇄 패턴 구조 
![](https://t1.daumcdn.net/cfile/tistory/99A62A4B5C5B846036)
### Handler 
- 요청을 수신하고 처리 객체들의 집합을 정의하는 인터페이스

### ConcreteHandler 
- 요청을 처리하는 실제 처리 객체
  - 핸들러에 대한 필드를 내부에 가지고 있으며 메서드를 통해 다음 핸들러를 체인시키고 다음을 바라봅니다. 
  - 자신이 처리할 수 없는 요구가 나오면 바라보고 있는 다음 체인의 핸들러에게 요청을 떠넘깁니다. 

### Client 
- 요청을 `Handler`에 전달합니다. 

여기서 핸들러끼리 체이닝 되는 구조는 어떤 형태이든 상관이 없습니다. 리스트형일수도 있고 선형 일 수도 있고 트리 형태일 수도 있습니다. 


## 책임 연쇄 패턴 특징 

### 패턴 사용 시기 
- 특정 요청을 2개 이상의 여러 객체에서 판별하고 처리해야 할 때 
- 특정 순서로 여러 핸들러리르 실행해야 하는 경우 
- 프로그램이 다양한 방식과 종류의 요청을 처리할 것으로 예상되지만 정확한 요청 유형과 순서를 미리 알 수 없는 경우 
- 요청을 처리할 수 있는 객체 집합이 동적으로 정의되어야 할 때
  - 체인 연결을 런타임에 동적으로 설정

### 패턴 장점
- 클라이언트는 처리 객체의 체인 집합 내부의 구조를 알 필요가 없습니다. 
- 각각의 체인은 자신이 해야하는 일만 하기 때문에 새로운 요청에 대한 처리객체 생성이 편리해집니다. 
- 클라이언트 코드를 변경하지 않고 핸들러를 체인에 동적으로 추가하거나 처리 순서를 변경하거나 삭제할 수 있어 유연해집니다. 
- 요청의 호출자와 수신자를 분리시킬 수 있습니다. 
  - 요청을 하는 쪽과 요청을 처리하는 쪽을 디커플링 시켜 결합도를 낮춥니다. 
  - 요청을 처리하는 방법이 바뀌더라도 호출자 코드는 변경되지 않습니다. 

### 패턴 단점 
- 실행 시에 코드의 흐름이 많아져서 과정을 살펴보거나 디버깅 및 테스트가 쉽지 않습니다. 
- 충분한 디버깅을 거치지 않을 경우 집합 내부에서 무한 사이클이 발생할 수 있습니다. 
- 요청이 반드시 수행된다는 보장이 없습니다. 
- 책임 연쇄로 인한 처리 지연 문제가 발생할 수 있습니다. 담나 이는 트레이드 오프로서 요청과 처리에 대한 관계가 고정적이고 속도가 중요하면 책임 연쇄 패턴을 유의하여야 합니다. 

## 책임 연쇄 패턴 예제 
### 클린하지 않은 코드 
일반적으는 아래와 같이 처리문을 하나의 메서드로 구성할 것입니다. 
```java
class UrlParser {
    public static void run(String url) {
        // protocol 파싱
        int index = url.indexOf("://");
        
        if (index != -1) {
            System.out.println("Protocol: " + url.substring(0, index));
        } else {
            System.out.println("No Protocol");
        }
        
        // domain 파싱
        int startIndex = url.indexOf("://");
        int lastIndex = url.indexOf(":");

        System.out.print("Domain: " );
        
        if (startIndex == -1) {
            if (lastIndex == -1) {
                System.out.println(url);
            } else {
                System.out.println(url.substring(0, lastIndex));
            }
        } else if (startIndex != lastIndex) {
            System.out.println(url.substring(startIndex + 3, lastIndex));
        } else {
            System.out.println(url.substring(startIndex + 3));
        }
        
        // port 파싱
        int index2 = url.lastIndexOf(":");
        
        if (index != -1) {
            String strPort = url.lastIndexOf(":");
            try {
                int port = Integer.parseInt(strPort);
                System.out.println("Port: " + port);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}
```

```java
class Client {
    public static void main(String[] args) {
        String url1 = "http://www.youtube.com:80";
        System.out.println("Input: " + url1);
        UrlParser.run(url1);
    }
}
```

동작에는 문제가 없지만, 이런식으로 구성할 경우 만일 `path`나 `queryString`도 구하는 새로운 처리 로직이 추가될 경우 메서드 로직을 통째로 수정해야 할 것이고 
이전의 로직과 겹치는게 없는지 복기해야 합니다. 당장 코드만 봐도 프로토콜, 도메인, 포트를 구하는데 있어 `indexOf()` 메서드를 자체적으로 사용하고 있어 겹치는 부분이 있는 것을 볼 수 있습니다. 

또한 사용자가 포트 정보는 원하지 않을 경우 코드를 지울 수는 없으니 포트 파싱 부분만 뺀 비슷한 코드로 이루어진 메서드를 따로 생성해야 합니다. 이는 요구에 대한 처리를 중앙집권적으로 모두 가지고 있기 때문에 
발생하는 현상입니다. 


### 책임 연쇄 패턴을 적용한 코드 
그렇다면 프로토콜만 파싱하는 책임과 도메인만을 파싱하는 책임 이렇게 처리 책임을 각 객체들로 분리하고 이들 끼리 체인으로 연결함으로써 좀 더 유연하게 프로그램 로직을 
구성할 수 있게 됩니다. 

핸들러는 본인의 역할만 수행하고 추가 처리 로직이 필요하다면 유연하게 체인을 추가해주면 되며 체인을 구조적으로 다양하게 구성할 수 있게 됩니다. 
```java
abstract class Handler {
    protected Handler nextHandler = null;
    
    public Handler setNext(Handler handler) {
        this.nextHandler = handler;
        return handler;
    }
    
    protected abstract void process(String url);
    
    public void run(String url) {
        process(url);
        
        if (nextHandler != null) {
            nextHandler.run(url);
        }
    }
}
```

```java
import javax.lang.model.element.NestingKind;
import java.text.NumberFormat;

class ProtocolHandler extends Handler {
    @Override
    protected void process(String url) {
        int index = url.indexOf("://");

        if (index != -1) {
            System.out.println("Protocol: " + url.substring(0, index));
        } else {
            System.out.println("No Protocol");
        }
    }
}

class DomainHandler extends Handler {
    @Override
    protected void process(String url) {
        int startIndex = url.indexOf("://");
        int lastIndex = url.indexOf(":");

        System.out.print("Domain: ");
        if (startIndex == -1) {
            if (lastIndex == -1) {
                System.out.println(url);
            } else {
                System.out.println(url.substring(0, lastIndex));
            }
        } else if (startIndex != lastIndex) {
            System.out.println(url.substring(startIndex + 3, lastIndex));
        } else {
            System.out.println(url.substring(startIndex + 3));
        }
    }
}

class PortHandler extends Handler {
    @Override
    protected void process(String url) {
        int index = url.lastIndexOf(":");

        if (index != -1) {
            String startPort = url.substring(index + 1);
            try {
                int port = Integer.parseInt(startPort);
                System.out.println("Port: " + port);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}
```

```java
class Client {
    public static void main(String[] args) {
        Handler handler1 = new ProtocolHandler();
        Handler handler2 = new DomainHandler();
        Handler handler3 = new PortHandler();
        
        handler1.setNext(handler2).setNext(handler3);
        
        String url1 = "http://www.youtube.com:80";
        System.out.println("Input: " + url1);
        handler.run(url1);
    }
}
```