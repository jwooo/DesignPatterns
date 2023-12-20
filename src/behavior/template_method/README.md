# TEMPLATE METHOD PATTERN
## 템플릿 메서드 패턴이란?
- 여러 클래스에서 공통으로 사용하는 메서드를 템플릿화 하여 상위 클래스에서 정의하고, 하위 클래스마다 세부 동작 사항을 다르게 구현하는 패턴입니다. 
- 즉, 변하지 않는 기능(템플릿)은 상위 클래스에 만들어두고 자주 변경되며 확장할 기능은 하위 클래스에 만들도록 하여, 상위의 메서드 실행 동작 순서는 고정하면서 세부 실행 내용은 다양화 될 수 있는 경우에 사용됩니다. 
- 해당 패턴은 상속이라는 기술을 극대화하여, 알고리즘의 뼈대를 맞추는 것에 초점을 둡니다. 

## 템플릿 메서드 패턴 구조
![](https://scvgoe.github.io/img/template_method.gif)
### AbstractClass (추상 클래스)
- 템플릿 메서드를 구현하고, 템플릿 메서드에서 돌아가는 추상 메서드를 선언합니다. 
- 이 추상 메서드는 하위 클래스인 `ConcreteClass` 역할에 의해 구현됩니다. 

### ConcreteClass (구현 클래스)
- `AbstractClass`를 상속하고 추상 메서드를 구체적으로 구현합니다. 
- `ConcreteClass`에서 구현한 메서드는 `AbstractClass`의 템플릿 메서드에서 호출됩니다. 

### hook 메서드 
- 훅 메서드는 부모의 템플릿 메서드의 영향이나 순서를 제어하고 싶을 때 사용되는 메서드 형태를 말합니다.
- 훅 메서드는 추상 메서드가 아닌 일반 메서드로 구현하는데, 선택적으로 오버라이드 하여 자식 클래스에서 제어하거나 아니면 놔두거나 하기 위해서 입니다. 


## 템플릿 메서드 패턴 특징 
### 패턴 사용 시기 
- 클라이언트가 알고리즘의 특정 단계만 확장하고, 전체 알고리즘이나 해당 구조는 확장하지 않도록 할때 
- 동일한 기능은 상위 클래스에서 정의하면서 확장, 변화가 필요한 부분만 하위 클래스에서 구현할 때 

### 패턴 장점
- 클라이언트가 대규모 알고리즘의 특정 부분만 재정의하도록 하여, 알고리즘의 다른 부분에 발생하는 변경 사항의 영향을 덜 받도록 한다. 
- 상위 추상 클래스로 로직을 공통화 하여 코드의 중복을 줄일 수 있다. 
- 서브 클래스의 역할을 줄이고, 핵심 로직을 상위 클래스에서 관리하므로 관리가 용이해진다. 

### 패턴 단점 
- 알고리즘의 제공된 골격에 의해 유연성이 제한될 수 있다. 
- 알고리즘 구조가 복잡할수록 템플릿 로직 형태를 유지하기 어려워진다. 
- 추상 메서드가 많아지면서 클래스의 생성, 관리가 어려워질 수 있다. 
- 상위 클래스에서 선언된 추상 메서드를 하위 클래스에서 구현할 때, 그 메서드가 어느 타이밍에서 호출되는지 클래스 로직을 이해해야 할 필요가 있다. 
- 로직에 변화가 생겨 상위 클래스를 수정할 때, 모든 서브 클래스의 수정이 필요할 수도 있다. 
- 하위 클래스를 통해 기본 단계 구현을 억제하여 리스코프 치환 법칙을 위반할 여지가 있다. 

## 템플릿 메서드 패턴 예제 

`number.txt` 파일에 적혀있는 숫자 값들을 읽어, 숫자들을 연산한 결과를 알려주는 기능을 구현해봅시다. 

### 클린하지 않은 코드 예제 
아래의 구성을 보면 `FileProcessor` 클래스에 `process()` 메서드에서 알고리즘을 수행하는 것을 볼 수 있습니다. 
`Client`의 메인 메서드에서 파일경로를 설정해주고 `process()`를 실행하면 모든 숫자들을 덧셈한 결과 15가 출력됩니다. 

```java
class FileProcessor {
    private String path;
    
    public FileProcessor(String path) {
        this.path = path;
    }
    
    public int process() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            int result = 0;
            String line = null;
            
            while ((line = reader.readLine()) != null) {
                result += Integer.parseInt(line);
            }
            return result;
        } catch (IOException e) {
            throw new IllegalArgumentException(path + "에 해당하는 파일이 없습니다.", e);
        }
    }
}

class Client {
    public static void main(String[] args) {
        FileProcessor fileProcessor = new FileProcessor("number.txt");
        
        int result = fileProcessor.process();
        System.out.println(result);
    }
}
```

그런데 만일 모든 숫자들을 곱셈하거나 나누는 추가적인 연산하는 기능이 필요하다고 하여 `MultiplyFileProcessor`라는 클래스를 정의하고 연산하는 라인을 곱셈으로 수정하였습니다. 
```java
class MultiplyFileProcessor {
    private String path;

    public MultiplyFileProcessor(String path) {
        this.path = path;
    }

    public int process() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            int result = 0;
            String line = null;

            while ((line = reader.readLine()) != null) {
                result *= Integer.parseInt(line);
            }
            return result;
        } catch (IOException e) {
            throw new IllegalArgumentException(path + "에 해당하는 파일이 없습니다.", e);
        }
    }
}

```
하지만 연산 부분만 다를 뿐이지 파일을 읽어와 읽는 알고리즘은 똑같습니다. 

즉, 코드의 중복이 발생한 것입니다. 보통 메서드 중복을 해결하기 위해 상속을 하고 부모 클래스에서 메서드를 정의하면 자식 클래스에서 가져와 사용하면 되지만, 위의 
메서드 로직을 보면 한두줄만 코드가 다른 상황입니다. 

그러면 공통된 부분만이라도 따로 메서드로 빼면 되는데, 코드 로직상 오히려 더 복잡해질 것만 같을때 반대로 다른 부분을 메서드로 빼는 템플릿 메서드 패턴을 사용하여 해결할 수 있습니다. 


### 템플릿 메서드 패턴을 적용한 코드 
먼저 `FileProcessor`를 추상 클래스로 만들고 `process()` 메서드를 템플릿 메서드로 명명합니다. 그리고 템플릿에서 실행되는 `calculate()` 메서드를 따로 추상 메서드로 만들어 자식 
클래스에서 구현을 하도록 유도 합니다.

```java
import java.io.BufferedReader;

abstract class FileProcessor {
    private String path;

    public FileProcessor(String path) {
        this.path = path;
    }

    public final int process() {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            int result = getResult();
            String line = null;
            
            while ((line = reader.readLine()) != null) {
                result = calculate(result, Integer.parseInt(line));
            }
            
            return result;
        } catch (IOException e) {
            throw new IllegalArgumentException(path + "에 해당하는 파일이 없습니다.");
        }
    }
    
    protected abstract int calculate(int result, int number);
    protected abstract int getResult();
}
```
이제 각 구현체들에서 추상 클래스를 상속하고 추상 메서드를 구현하여 알고리즘 로직을 설정해주면, 추상 클래스에서 미리 정의된 템플릿 메서드의 로직에 따라 각기 다른 알고리즘을 ㅅ행하게 됩니다. 

중복되는 코드들은 추상 클래스에 모아두고 다른 부분만 각 클래스들이 구현하도록 하여 가독성을 높이고 중복은 제거시킨 것입니다. 

```java
class PlusFileProcessor extends FileProcessor {
    public PlusFileProcessor(String path) {
        super(path);
    }
    
    @Override
    protected int calculate(int result, int number) {
        return result += number;
    }
    
    @Override
    protected int getResult() {
        return 0;
    }
}

class MultiplyFileProcessor extends FileProcessor {
    public MultiplyFileProcessor(String path) {
        super(path);
    }
    
    @Override
    protected int calculate(int result, int number) {
        return result *= number;
    }
    
    @Override
    protected int getResult() {
        return 1;
    }
}
```
```java
class Client {
    public static void main(String[] args) {
        PlusFileProcessor plusFileProcessor = new PlusFileProcessor("numbers.txt");
        int result1 = plusFileProcessor.process();
        System.out.println(result1);
        
        MultiplyFileProcessor multiplyFileProcessor = new MultiplyFileProcessor("numbers.txt");
        int result2 = multiplyFileProcessor.process();
        System.out.println(result2);
    }
}
```