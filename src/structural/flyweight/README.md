# FLYWEIGHT PATTERN

## 플라이웨이트 패턴이란? 
- 재사용 가능한 객체 인스턴스를 공유시켜 메모리 사용량을 최소화하는 구조 패턴입니다. 
- 캐시 개념을 코드로 패턴화 한것으로 보면 되는데, 자주 변화하는 속성과 변하지 않는 속성을 분리하고 변하지 않는 속성을 캐시하여 재사용해 메모리를 줄이는 방식입니다. 
- 동일하거나 유사한 객체들 사이에 가능한 많은 데이터를 서로 공유하여 사용하도록 하여 최적화를 노리는 경량 패턴이라고도 불립니다. 

## 플라이웨이트 패턴 구조
![](https://miro.medium.com/v2/resize:fit:1400/0*zyLsPQoVmlEzCGdk)

### Flyweight
- 경량 객체를 묶는 인터페이스
### ConcreteFlyweight
- 공유 가능하여 재사용되는 객체 
### UnsharedConcreteFlyweight
- 공유 불가능한 객체
### FlyweightFactory
- 경량 객체를 만드는 공장 역할과 캐시 역할을 겸비하는 `Flyweight` 객체 관리 클래스
    - `GetFlyweight()`메서드는 팩토리 메서드 역할을 한다고 보면 됩니다.
    - 만일 객체가 메모리에 존재한다면 그대로 가져와 반환하고, 없다면 새로 생성해 반환합니다. 
### Client
- 클라이언트는 `FlyweightFactory`를 통해 `Flayweight` 타입의 객체를 얻어 사용합니다. 


## 공유 가능과 공유 불가능
플라이 웨이트 패턴에서 가장 주의 깊게 보아야 할 점이 바로 공유 가능과 불가능 상태를 구분하는 것입니다. 

공유 가능이란 본질적인 상태를 의미하며 인스턴스가 어떠한 상황이에서도 변하지 않는 정보를 의미합니다. 그래서 값이 고정되어 있기에 충분히 언제 어디서 공유해도 문제가 되지 않습니다. 
공유 불가능이란 인스턴스를 두는 장소나 상황에 따라서 변화하는 정보를 의미합니다. 그래서 값이 언제 어디서 변화할지 모르기 때문에 이를 캐시해서 공유할 수는 없습니다. 


## 플라이웨이트 패턴의 특징 
### 패턴 사용 시기
- 애플리케이션에 의해 생성되는 객체의 수가 많아 저장비용이 높을때
- 생성된 객체가 오래도록 메모리에 상주하며 사용되는 횟수가 많을때
- 공통적인 인스턴스를 많이 생성하는 로직이 포함된 경우
- 임베디드와 같이 메모리를 최소한으로 사용해야하는 경우에 활용

### 패턴 장점
- 애플리케이션에서 사용하는 메모리를 줄일 수 있습니다. 
- 프로그램 속도를 개선할 수 있습니다. 
  - `new`로 인스턴스화를 하면 데이터가 생성되고 메모리에 적재되는 미량의 시간이 걸리게 됩니다. 
  - 객체를 공유하면 인스턴스를 가져오기만 하면 되기 때문에 메모리 뿐만 아니라 속도도 향상시킬 수 있습니다. 

### 패턴 단점
- 코드의 복잡도가 상승합니다. 

## Flyweight 패턴 예제
마인크래프트에서 숲을 구현하기 위해 지형에 나무 객체들을 심으려고 합니다. 
이 나무 객체에 대해 필요한 데이터는 다음과 같다고 한다고 가정해보겠습니다. 
1. 나무 종류
2. 메시 폴리곤
3. 나무껍질 텍스쳐
4. 잎사귀 텍스쳐
5. 위치 매개변수 

나무에도 여러가지 종류의 나무가 있으며, 나무의 형태를 구현하는 폴리곤과 텍스쳐 그리고 나무가 어느 지형 좌표에 심어질 지에 대한 x, y 위치 매개변수가 필요합니다. 

최적화의 핵심은 나무가 수백 그루 넘게 있는다 해도 대부분 비슷하게 보인다는 것입니다. 즉, 나무를 생성하는데 사용되는 `mesh`와 `texture`를 재사용하여 표현해도 어차피 같은 나무이니 상관이 없습니다. 
따라서 공통으로 사용되는 모델 데이터와 실시간으로 변화하는 위치 매개 변수를 분리하여 객체를 구성해주면, 지형에서 나무를 구현할 때 나무 모델 인스턴스 하나를 공유받고 위치 매개변수만 다르게 설정해주면 
메모리 사용량을 줄일 수 있습니다.

### 1. 공유 가능 객체와 공유 불가능 객체 쪼개기
똑같은 메시와 텍스쳐를 일일히 여러번 메모리에 올릴 이유가 없기 때문에 공유되는 나무 모델 객체를 기존 `Tree` 클래스에서 따로 빼줍니다. 
그러면 아래와 같이 `TreeModel` 클래스는 `ConcreteFlyweight`가 되고 좌표값을 가지고 있는 기존 `Tree` 클래스는 `UnsharedConreteFlyweight`가 됩니다. 

```java
final class TreeModel {
  long objSize = 90;
  
  String type;
  Object mesh;
  Object texture;
  
  public TreeModel(String type, Object mesh, Object texture) {
    this.type = type;
    this.mesh = mesh;
    this.texture = texture;
    
    Memory.size += this.objSize;
  }
}
```
```java
class Tree {
  long objSize = 10;
  
  double position_x;
  double position_y;
  
  TreeModel model;
  
  public Tree(TreeModel model, double position_x, double position_y) {
    this.model = model;
    this.position_x = position_x;
    this.position_y = position_y;
    
    Memory.size += this.objSize;
  }
}
```
이때 `Tree` 클래스와 `TreeModel` 간의 관계를 맺어주어야 하는데, 상속을 통해 해주어도 되고, 위와 같이 합성을 통해 맺어주어도 됩니다. 
그리고 `ConcreteFlyweight`인 `TreeModel` 클래스를 `final`화 시켜 불변 객체로 만들어줍니다. 나무 모델은 중간에 메시와 텍스쳐가 변경될 일이 없기 때문입니다. 

### 2. Flyweight 팩토리 만들기 
나무 모델 객체에 플라이웨이트를 적용하였으니 이를 생성하고 관리하는 `FlyweightFactory` 클래스를 만들어야 합니다. 
플라이웨이트 팩토리의 핵심은 다음과 같습니다. 
1. `Flyweight Pool`: `HashMap` 컬렉션을 통해 키와 나무 모델 객체를 저장하는 캐시 저장소 역할
2. `getInstance`: `Pool`에 가져오고자 하는 객체가 있는지 검사를 하여 있으면 그대로 반환, 없으면 새로 생성하는 역할

```java
class TreeModelFactory {
  private static final Map<String, TreeModel> cache = new HashMap<>();
  
  public static TreeModel getInstance(String key) {
    if (cache.containsKey(key)) {
        return cache.get(key);
    } else {
      TreeModel model = new TreeModel(key, new Object(), new Object());
      System.out.println("-- 나무 모델 객체 새로 생성 완료 --");
      
      cache.put(key, model);
      
      return model;
    }
  }
}
```

### 3. Client 사용
```java
// Client
class Terrain {
  static final int CANVAS_SIZE = 10000;
  
  public void render(String type, double position_x, double position_y) {
    TreeModel model = TreeModelFactory.getInstance(type);
    
    Tree tree = new Tree(model, position_x, position_y);
    
    System.out.println("x: " + tree.position_x + " y: " + tree.position_y + " 위치에 " + type + " 나무 생성 완료");
  }
}
```
```java
class Test {
  public static void main(String[] args) {
    Terrian terrian = new Terrian();
    
    for (int i = 0; i < 5; i++) {
        terrian.render("Oak", Math.random() * Terrian.CANVAS_SIZE, Math.random() * Terrian.CANVAS_SIZE);
    }

    for (int i = 0; i < 5; i++) {
      terrian.render("Acacia", Math.random() * Terrian.CANVAS_SIZE, Math.random() * Terrian.CANVAS_SIZE);
    }
    
    for (int i = 0; i < 5; i++) {
      terrian.render("Jungle", Math.random() * Terrian.CANVAS_SIZE, Math.random() * Terrian.CANVAS_SIZE);
    }
    
    Memory.print();
  }
}
```
이러한 방식으로 코드를 작성하면 통으로 나무 객체를 생성할때에 비해 중복된 메시와 텍스처 사용을 공유시켜 메로리를 아낄 수 있습니다. 

이처럼 공유할 수 있는 공유 가능한 데이터를 분간하여 캐싱함으로써 프로그램을 최적화 할 수 있게 되었습니다. 

