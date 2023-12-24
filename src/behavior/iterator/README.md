# ITERATOR PATTERN
## 반복자 패턴이란? 
- 반복자 패턴은 일련의 데이터 집합에 대하여 순차적인 접근(순회)을 지원하는 패턴입니다. 
  - 데이터 집합이란 객체들을 그룹으로 묶어 자료의 구조를 취하는 컬렉션을 말합니다. 
  - 대표적인 컬렉션으로 리스트, 트리, 테이블 등이 있습니다. 

보통 배열이나 리스트와 같은 경우 순서가 연속적인 데이터 집합이기 때문에 간단한 `for`문을 통해 순회할 수 있습니다.
그러나 해시, 트리와 같은 컬렉션은 데이터 저장 순서가 정해지지 않고 적재되기 때문에, 각 요소들을 어떤 기준으로 접근해야 지 애매해집니다. 

예를 들어 트리 구조가 있다면 어떤 상황에서는 깊이를 우선으로 순회해야 할 수도 있고, 너비를 우선으로 순회할 수도 있습니다. 
이처럼 복잡하게 얽혀있는 자료 컬렉션들을 순회하는 알고리즘 전략을 정의하는 것을 이터레이터 패턴이라고 합니다. 

컬렉션 객체 안에 들어있는 모든 원소들에 대한 접근 방식이 공통화 되어 있다면 어떤 종류의 컬렉션에서도 이터레이터만 뽑아내면 여러 전략으로 순회가 가능해 보다 다형적인 코드를 설계할 수 있게 됩니다. 

## 반복자 패턴 구조 
![](https://t1.daumcdn.net/cfile/tistory/99D918335C60E30F33)
### Aggreate
- `ConcreateIterator` 객체를 반환하는 인터페이스를 제공한다. 
  - `iterator()`: `ConcreteInterator` 객체를 만드는 팩토리 메서드
### ConcreteAggregate
- 여러 요소들이 이루어져 있는 데이터 집합체
### Iterator
- 집합체 내의 요소들을 순서대로 검색하기 위한 인터페이스를 제공한다. 
  - `hasNext()`: 순회할 다음 요소가 있는지 확인
  - `next()`: 요소를 반환하고 다음 요소를 반환할 준비를 하기 위해 커서를 이동시킴
### ConcreteIterator
- `ConcreteAggregate`가 구현한 메서드로부터 생성되며, `ConcreteAggregate`의 컬렉션을 참조하여 순회한다. 
- 어떤 전략으로 순회할지에 대한 로직을 구체화 한다.

## 반복자 패턴 흐름
```java
interface Aggregate {
  Iterator iterator();
}

class ConcreteAggregate implements Aggregate {
  Object[] arr;
  int index = 0;
  
  public ConcreteAggregate(int size) {
    this.arr = new Object[size];
  }
  
  public void add(Object o) {
    if (index < arr.length) {
      arr[index] = o;
      index++;
    }
  }
  
  @Override
  public Iterator iterator() {
    return new ConcreteIterator(arr);
  }
}
```

```java
interface Iterator {
  boolean hasNext();
  Object next();
}

class ConcreteIterator implements Iterator {
  Object[] arr;
  private int nextIndex = 0;
  
  public ConcreteIterator(Object[] arr) {
    this.arr = arr;
  }
  
  @Override
  public boolean hasNext() {
    return nextIndex < arr.length;
  }
  
  @Override
  public Object next() {
    return arr[nextIndex++];
  }
}
```

```java
class Client {
  public static void main(String[] args) {
    ConcreteAggregate aggregate = new ConcreteAggregate(5);
    
    aggregate.add(1);
    aggregate.add(2);
    aggregate.add(3);
    aggregate.add(4);
    aggregate.add(5);
    
    Iterator iter = aggregate.iterator();
    
    while (iter.hasNext()) {
      System.out.printf("$s -> ", iter.next());
    }
  }
}
```

## 반복자 패턴 특징
### 패턴 사용 시기
- 컬렉션에 상관없이 객체 접근 순회 방식을 통일하고자 할 때
- 컬렉션을 순회하는 다양한 방법을 지원하고 싶을 때 
- 컬렉션의 복잡한 내부 구조를 클라이언트로 부터 숨기고 싶은 경우 
- 데이터 저장 컬렉션 종류가 변경 가능성이 있을 때 
  - 클라이언트가 집합 객체 내부 표현 방식을 알고 있다면, 표현 방식이 달라지면 클라이언트 코드도 변경되어야 한다는 문제가 생깁니다. 

### 패턴 장점
- 일관된 이터레이터 인터페이스를 사용해 여러 형태의 컬렉션에 대해 동일한 순회 방법을 제공합니다. 
- 컬렉션의 내부 구조 및 순회 방식을 알 지 않아도 됩니다. 
- 집합체의 구현과 접근하는 처리 부분을 반복자 객체로 분리해 결합도를 줄일 수 있습니다. 
  - `Client`에서 `iterator`로 접근하기 때문에 `ConcreteAggregate` 내에 수정 사항이 생겨도 `iterator`에 문제가 없다면 문제가 발생하지 않습니다. 
- 순회 알고리즘을 별도의 반복자 객체에 추출하여 각 클래스의 책임을 분리하여 단일 책임 원칙을 준수합니다. 
- 데이터 저장 컬렉션 종류가 변경되어도 클라이언트 구현 코드는 손상되지 않아 수정에는 닫혀 있어 개방 폐쇄 원칙을 준수합니다. 

### 패턴 단점
- 클래스가 늘어나고 복잡도가 증가합니다. 
  - 만일 앱이 간단한 컬렉션에서만 작동하는 경우 패턴을 적용하는 것은 복잡도만 증가할 수 있습니다. 
  - 이터레이터 객체를 만드는 것이 유용한 상황인지 판단할 필요가 있습니다.
- 구현 방법에 따라 캡슐화를 위배할 수 있습니다.

## 반복자 패턴 예제 
요구사항은 게시판에 글을 올릴건데, 게시글을 최근글, 작성순으로 정렬하서 나열할 수 있게 해달라고 합니다. 
즉, 두가지 정렬 전략을 구현해야 되는 것입니다. 

### 클린하지 못한 예제 
다음은 게시글과 게시판을 표현한 인스턴스 입니다. 
게시글에는 게시글 제목과 `title`과 게시글 발행 날짜 필드가 있습니다. 
```java
class Post {
  String title;
  LocalDate date;
  
  public Post(String title, LocalDate date) {
    this.title = title;
    this.date = date;
  }
}

class Board {
  List<Post> posts = new ArrayList<>();
  
  public void addPost(String title, LocalDate date) {
    this.posts.add(new Post(title, date));
  }
  
  public List<Post> getPosts() {
    return posts;
  }
}
```
```java
class Client {
  public static void main(String[] args) {
    Board board = new Board();

    board.addPost("board1", LocalDate.of(2020, 8, 30));
    board.addPost("board2", LocalDate.of(2020, 2, 6));
    board.addPost("board3", LocalDate.of(2020, 6, 1));
    board.addPost("board4", LocalDate.of(2021, 12, 22));
    
    List<Post> posts = board.getPosts();
    
    for (int i = 0; i < posts.size(); i++) {
        Post post = posts.get(i);
        System.out.println(post.title + "/" + post.date);
    }
    
    Collections.sort(posts, (p1, p2) -> p1.date.compareTo(p2.date));
    for (int i = 0; i < posts.size(); i++) {
      Post post = posts.get(i);
      System.out.println(post.title + "/" + post.date);
    }
  }
}
```
일반적으로 `for`문을 돌려 집합체의 요소들을 순회하였습니다. 그러나 이러한 구성 방식은 `Board`에 들어간 `Post`를 순회할 때, 
`Board`가 어떠한 구조로 이루어져있는지를 클라이언트에 노출하게 됩니다. 

따라서 이를 객체 지향적으로 구성하기 위해 반복자 패턴을 적용해보도록 하겠습니다.

### 반복자 패턴을 적용한 코드 
위에서 이터레이터 인터페이스를 직접 만들어 사용하였지만, 자바에서는 이미 이터레이터 인터페이스를 지원합니다. 자바의 내부 이터레이터를 재활용하여 메서드 위임을 통해 코드를 간단하게 구현할 수도 있습니다. 

순회 전략으로는 리스트 저장 순서대로 조회와 날짜 순서대로 조회 두가지가 존재합니다. 따라서 이에 대한 이터레이터 클래스 역시 두가지 생성해주어야 합니다. 
- `ListPostIterator`: 저장 순서 이터레이터
- `DatePostIterator`: 날짜 순서 이터레이터
그리고 `ListPostIterator`와 `DatePostIterator` 객체를 반환하는 팩토리 메서드를 `Board` 클래스에 추가만 해주면 됩니다. 

```java
class ListPostIterator implements Iterator<Post> {
    private Iterator<Post> iter;

    public ListPostIterator(List<Post> posts) {
        this.iter = posts.iterator();
    }

    @Override
    public boolean hasNext() {
        return this.iter.hasNext();
    }

    @Override
    public Post next() {
        return this.iter.next();
    }
}

class DatePostIterator implements Iterator<Post> {
  private Iterator<Post> iter;
  
  public DatePostIterator(List<Post> posts) {
    Collections.sort(posts, (p1, p2) -> p1.date.compareTo(p2.date));
    this.iter = posts.iterator();
  }
  
  @Override
  public boolean hasNext() {
    return this.iter.hasNext();
  }
  
  @Override
  public Post next() {
    return this.iter.next();
  }
}
```

```java
class Board {
  List<Post> posts = new ArrayList<>();
  
  public void addPost(String title, LocalDate date) {
    this.posts.add(new Post(title, date));
  }
  
  public List<Post> getPosts() {
    return posts;
  }
  
  public Iterator<Post> getListPostIterator() {
    return new ListPostIterator(posts);
  }
  
  public Iterator<Post> getDatePostIterator() {
    return new DatePostIterator(posts);
  }
}
```

```java
class Client {
  public static void main(String[] args) {
    Board board = new Board();

    board.addPost("board1", LocalDate.of(2020, 8, 30));
    board.addPost("board2", LocalDate.of(2020, 2, 6));
    board.addPost("board3", LocalDate.of(2020, 6, 1));
    board.addPost("board4", LocalDate.of(2021, 12, 22));
   
    print(board.getListPostIterator());
    print(board.getDatePostIterator());
  }
  
  public static void print(Iterator<Post> iterator) {
    while (iterator.hasNext()) {
      Post post = iter.next();
      System.out.println(post.title + "/" + post.date);
    }
  }
}
```
이제 클라이언트는 게시글을 순회할때 `Board` 내부가 어떤 집합체로 구현되어 있는지 알 수 없게 감추고 전혀 신경쓸 필요가 없게 되었습니다. 
그리고 순회 전략을 각 객체로 나눔으로써 때에 따라 적절한 이터레이터 객체만 받으면 똑같은 이터레이터 순회 코드로 다양한 순회 전략을 구사할 수 있게 되었습니다. 
