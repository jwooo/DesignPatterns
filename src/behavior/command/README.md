# COMMAND PATTERN
## 커맨드 패턴이란? 
- 커맨드 패턴은 특정 객체에 대한 커맨드 객체를 필요에 따라 처리하는 패턴입니다. 
- 보통 주체 객체 -> 대상 객체와 같은 방식으로 호출한다면 대상 객체에 대한 액션은 주체 객체에서 메서드로 처리하는데 이 액션을 객체로 만들어 처리하는 방식입니다. 
- 실행될 기능을 캡슐화 함으로써 여러 기능을 실행할 수 있어 재사용성이 높으며 기능이 수정되거나 변경이 일어났을 때, 주체 객체의 코드 수정 없이 기능에 대한 클래스만 정의하면 되어 확장성이 유연해집니다. 

## 커맨드 패턴 구조
![](https://images.velog.io/images/ayoung0073/post/3f6fbd8a-61d1-4bee-b4d7-aac07238234c/image.png)

### Invoker
- 기능의 실행을 요청하는 호출자 클래스
- `Client`의 요청을 받아 `Receiver`의 액션을 호출

### Command
- 실행될 기능에 대한 인터페이스 
- 실행될 기능을 `execute()`로 정의 

### ConcreteCommand
- 실행되는 기능을 구현하는 클래스
- `Receiver`가 무엇을 처리해야 하는지 `execute()`에 세부 구현 정의 

### Receiver
- `ConcreteCommand`에서 `execute()`를 구현할때 필요한 클래스 
- `ConcreteCommand`의 기능을 실행하기 위해 사용하는 수신자 클래스 
- `Clinet`가 요청한 내용에 대해 액션만 취해주면 됨

## 커맨드 패턴 특징 
### 사용 시기 
- 수행할 작업을 매개변수화 하려고 할 때 
- 요청에 대하여 큐잉하거나 특정 시점에 실행하고 싶을때
- 작업 취소 기능이나 변경 사항에 대한 로깅을 위해 사용

### 장점 
- 작업을 수행하는 객체와 요청하는 객체를 분리하여 단일 책임 원칙에 부합
- 코드의 수정 없이 작업 수행 객체나 추가 구현이 가능하여 개방-폐쇄 원칙에 부합
- 커맨드 단위의 액션이 가능 

### 단점
- 구조가 간단하지 않아 구현하는데 어려움이 있음 
- 리시버 객체의 동작이 늘어날 때 마다 커맨드 클래스가 늘어나기 때문에 클래스가 많아진다. 

## 커맨드 패턴 예제 
`Command`는 호출할 메서드, 메서드를 구현하는 객체를 포함하여 작업을 실행하는데 필요한 모든 정보를 저장하는 역할을 하는 객체입니다. 

아래 예제의 경우 `TextFileOperation` 인터페이스 같은 경우 `Command` 객체의 API를 정의하고 `OpenTextFileOperation`과 `SaveTextFileOperation`의 
두 구현이 구체적인 작업을 수행합니다. 전자는 텍스트 파일을 열고 후자는 텍스트 파일을 저장합니다. 
```java
@FunctionalInterface
public interface TextFileOperation {
    String execute();
}

public class OpenTextFileOperation implements TextFileOperation {
    private TextFile textFile;
    
    @Override
    public String execute() {
        return textFile.open();
    }
}

public class SaveTextFileOperation implements TextFileOperation {
    private TextFile textFile;
    
    @Override
    public String execute() {
        return textFile.execute();
    }
}
```

`Receiver`는 일련의 응집력 있는 작업을 수행하는 객체입니다. 명령의 `execute()` 메서드가 호출될 때 실제 작업을 수행하는 구성요소 입니다. 
이 경우에는 `TextFile` 객체를 모델링하는 역할을 수신자 클래스에서 정의해야 합니다. 
```java
public class TextFile {
    private String name;
    
    public String open() {
        return "Opening file " + name;
    }
    
    public String save() {
        return "Save file " + name;
    }
}
```

`TextFileExecuter` 클래스는 `Invoker`의 역할을 하며 명령 개체를 분리하고 `TextFileOperation` 커맨드 객체 내에 캡슐화된 메서드를 호출하는 추상화 계층 입니다. 
이 경우 클래스는 커맨드 객체도 `List`에 저장합니다. 물론 작업 실행 프로세스에 추가 제어를 하는 경우가 아니면 패턴 구현에서 이는 필수는 아닙니다. 
```java
public class TextFileOperationExecuter {
    private final List<TextFileOperation> textFileOperations = new ArrayList<>();
    
    public String executeOperation(TextFileOperation textFileOperation) {
        textFileOperation.add(textFileOperation);
        return textFileOperation;
    }
}
```

```java
class Client {
    public static void main(String[] args) {
        String openFile = textFileOperationExecutor.executeOperation(new OpenTextFileOperation(new TextFile("file1.txt")));
        String saveFile = textFileOperationExecutor.executeOperation(new SaveTextFileOperation(new TextFile("file2.txt")));

        System.out.println(openFile);
        System.out.println(saveFile);

        TextFile textFile = new TextFile("file3.txt");
        String openFileMethod = textFileOperationExecutor.executeOperation(textFile::open);
        String saveFileMethod = textFileOperationExecutor.executeOperation(textFile::save);

        System.out.println(openFileMethod);
        System.out.println(saveFileMethod);
    }
}
```