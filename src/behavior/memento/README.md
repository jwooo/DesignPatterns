# MEMENTO PATTERN
## 메멘토 패턴이란? 
- 객체의 상태 정보를 가지는 클래스를 따로 생성하여, 객체의 상태를 저장하거나 이전 상태로 복원할 수 있게 해주는 패턴
- 원하는 시점의 상태 복원이 가능하다.

## 메멘토 패턴 구조
![](https://velog.velcdn.com/images/weekbelt/post/c14c0d5b-3d44-4848-9ee3-b8521e82fb8b/image.png)
### 오리지네이터 (`Originator`)
- 자신의 상태에 대한 스냅샷들을 생성할 수 있으며, 필요시 스냅샷에서 자신의 상태를 복원할 수도 있다. 
### 메멘토 (`Memento`)
- 오리지네이터의 상태의 스냅샷 역할을 하는 값 객체
- 관행적으로 메멘토는 불변으로 만든 후 생성자를 통해 데이터를 한 번만 전달한다. 
### 케어테이커 (`CareTaker`)
- 메멘토들의 스택을 저장하여 오리지네이터의 기록을 추적할 수 있다. 
- 과거로 돌아가야 할 때 케어테이커는 맨 위의 메멘토를 스택에서 가져온 후 오리지네이터 복원 메서드에 전달한다.

## 메멘토 패턴 특징
### 메멘토 패턴 장점
- 캡슐화를 위반하지 않고 객체의 상태의 스냅샷들을 생성할 수 있다. 
- 케어테이커가 오리지네이터의 상태의 기록을 유지하도록 하여 오리지네이터의 코드를 단순화할 수 있다. 
### 메멘토 패턴 단점
- 클라이언트들이 메멘토들을 너무 자주 생성하면 많은 RAM을 소모할 수 있다. 
- 케어테이커들은 더 이상 쓸모없는 메멘토들을 파괴할 수 있도록 오리지네이터의 수명주기를 추적해야 한다. 
### 메멘토 패턴 사용 시기
- 사용자가 객체 상태에 대한 변경 사항을 되돌릴 수 있도록 애플리케이션에서 실행 취소 기능을 구현해야 하는 경우
- 버전 관리 또는 검사점과 같은 기능을 지원하기 위해 다양한 시점의 객체 상태를 저장해야 하는 경우
- 데이터베이스 트랜잭션과 같이 오류나 예외가 발생한 경우 객체 상태에 대한 변경 사항을 롤백해야 하는 경우
- 성능을 향상하거나 중복 계산을 줄이기 위해 객체의 상태를 캐시하려는 경우
### 메멘토 패턴 미사용 시기
- 객체의 상태가 크거나 복잡한 경우 해당 상태의 여러 스냅샷을 저장하고 관리하는 데 상당한 양의 메모리와 처리 리소스가 소비될 수 있는 경우
- 객체의 상태가 예측할 수 없게 자주 변경되는 경우
- 객체의 상태가 불변이거나 쉽게 재구성 가능한 경우

## 메멘토 패턴 예제
아래 예제는 텍스트 편집기 애플리케이션 입니다. 

사용자가 문서에 대한 변경 사항을 되돌릴 수 있는 실행 취소 기능을 구현하고 싶다고 가정해보자. 문제는 문서 내부 구현을 노출하지 않고  
다양한 시점의 문서 상태를 필요할 때 복원하는 것이다. 

일단 문서를 저장할 `Document` 객체를 생성한다.

```java
public class Document {

    private String content;

    public Document(String content) {
        this.content = content;
    }

    public void write(String text) {
        this.content += text;
    }

    public String getContent() {
        return this.content;
    }

    public DocumentMemento createMemento() {
        return new DocumentMemento(this.content);
    }
    
    public void restore(DocumentMemento memento) {
        this.content = memento.getContent();
    }

}
```
해당 객체에서는 `content`를 통해 내용을 저장합니다. 이후 저장한 내용을 이전으로 되돌릴 수 있도록 `createMemento`와 `restore` 메서드를 정의합니다.

```java
public class DocumentMemento {

    private String content;

    public DocumentMemento(String content) {
        this.content = content;
    }
    
    public String getContent() {
        return this.content;
    }
}
```
`DocumentMemento`는 `Document`의 정보를 이전으로 되돌릴 수 있도록 정보를 저장하는 객체입니다.

```java
public class History {

    private List<DocumentMemento> mementos = new ArrayList<>();

    public void add(DocumentMemento memento) {
        this.mementos.add(memento);
    }

    public DocumentMemento getMemento(int index) {
        return this.mementos.get(index);
    }

}
```
`History`는 `DocumentMemento`를 관리하는 `CareTaker`의 역할을 하고 있습니다. 이를 통해 메멘토의 정보를 저장하거나 특정 인덱스에 저장된 메멘토 객체를 리턴 해줍니다.

```java
public class Client {
    public static void main(String[] args) {
        Document document = new Document("글을 작성 합니다.");
        History history = new History();

        document.write("\n글을 1번째로 추가 작성 합니다.");
        history.add(document.createMemento());

        document.write("\n글을 2번째로 추가 작성 합니다.");
        history.add(document.createMemento());

        document.restoreFromMemento(history.getMemento(0));

        System.out.println(document.getContent());
    }
}
```
위의 코드를 수행 시키면 편집 파일에서 `Ctrl + Z` 처럼 이전에 작성한 텍스트로 변환되는 방식으로 동작하도록 구현되었다는 것을 알 수 있다.