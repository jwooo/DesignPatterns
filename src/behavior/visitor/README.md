# VISITOR PATTERN
## 방문자 패턴이란? 
- 데이터 구조와 데이터 처리를 분라히여 데이터 구조를 변경하지 않고도 새로운 연산을 추가할 수 있는 패턴 
- 이 패턴은 핵심 아이디어는 객체 구조 내의 각 객체에 대해 특정 작업을 수행하기 위한 연산을 객체 자체에서 분리하여 별도의 객체로 정의하는 것
- 이렇게 하면 새로운 연산이 추가될 때 기존의 객체 구조를 변경하지 않고도 각 객체에 새로운 연산을 수행할 수 있다. 

## 방문자 패턴 구조 
![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fled91%2Fbtrpjx2dYpF%2FKrHg2VqJKC7whnneUdWMq1%2Fimg.png)
### Visitor
- 객체 구조의 구상 요소들을 인수로 사용할 수 있는 `visit` 메서드들의 집합을 선언한다. 
- 이러한 메서드들은 같은 이름을 가질 수 있지만 그들의 매개변수의 유형은 달라야 한다.
### ConcreteVisitor
- 다양한 구상 요소 클래스들에 맞춤으로 작성된 같은 행동들의 여러 버전을 구현한다. 
### Element 
- `Visitor`를 `accept`하는 메서드를 선언한다. 
- 이 메서드에는 비지터 인터페이스 유형으로 선언된 하나의 매개변수가 들어 있어야 한다. 
### ElementXXX
- 반드시 `accept` 메서드를 구현해야 한다. 
- 이 메서드의 목적은 호출을 현재 요소 클래스에 해당하는 적절한 비지터 메서드로 리다이렉트 하는 것이다. 
- 기초 요소 클래스가 이 메서드를 구현하더라도 모든 자식 클래스들은 여전히 자신들의 클래스들 내에서 이 메서드를 오버라이드해야 하며 비지터 객체에 적절한 메서드를 호출해야 한다. 


## 방문자 패턴 특징
### 방문자 패턴 장점
- 개방/폐쇄 원칙이 적용되어 다른 클래스를 변경하지 않으면서 해당 클래스의 객체와 작동할 수 있는 새로운 행동을 도입할 수 있다. 
- 단일 책임 원칙이 적용되어 같은 행동의 여러 버전을 같은 클래스로 이동할 수 있다. 
- 비지터 객체는 다양한 객체들과 작업하면서 유용한 정보를 축적할 수 있어 객체 트리와 같은 복잡한 객체 구조를 순회하여 이 구조의 각 객체에 비지터 패턴을 적용하려는 경우에 유용할 수 있다. 

### 방문자 패턴 단점
- 클래스가 요소 계층구조에 추가되거나 제거될 때마다 모든 비지터를 업데이트 해야 한다. 
- 비지터들은 함께 작업해야 하는 요소들의 비공개 필드들 및 메서드들에 접근하기 위해 필요한 권한이 부족할 수 있다.

## 방문자 패턴 예제 
아래 예시는 모양들의 집합을 `XML`로 내보내려고 한다. 문제는 우리 모양들의 코드를 직접 변경하고 싶지 않고, 변경하더라도 
최소한의 변경만 수행하려고 한다. 

최종적으로 방문자 패턴은 모양 계층 구조 클래스들의 기존 코드를 변경하지 않고 해당 계층 구조에 모든 행동들을 추가할 수 있도록 하는 
인프라를 구축한다. 

### 모양 관련 클래스 
```java
public interface Shape {
    String accept(Visitor visitor);
}
```
```java
@Getter
@AllArgsConstructor
public class Dot implements Shape {
    
    private int id;
    private int x;
    private int y;
    
    @Override
    public String accept(Visitor visitor) {
        return visitor.visitDot(this);
    }
    
}
```
```java
@Getter
public class Circle extends Dot {
    
    private int radius;
    
    public Circle(int id, int x, int y, int radius) {
        super(id, x, y);
        this.radius = radius;
    }
    
    @Override
    public String accept(Visitor visitor) {
        return visitor.visitCircle(this);
    }
    
}
```
```java
@Getter
@AllArgsConstructor
public class Rectangle implements Shape {
    
    private int id;
    private int x;
    private int y;
    private int width;
    private int height; 
 
    @Override
    public String accept(Visitor visitor) {
        return visitor.visitRectangle(this);
    }
    
}
```
```java
import java.util.ArrayList;

@Getter
public class CompoundShape implements Shape {

    private int id;
    private List<Shape> children = new ArrayList<>();
    
    public CompoundShape(int id) {
        this.id = id;
    }
    
    @Override
    public String accept(Visitor visitor) {
        return visitor.visitCompoundGraphic(this);
    }
    
    public void add(Shape shape) {
        children.add(shape);
    }
    
}
```
도형 관련 클래스 들은 `visitor`에 따라 다르게 동작하기 위해 `accept` 메서드를 오버라이딩 하여 구현하고 있다. 

### 방문자 관련 클래스 
```java
public interface Visitor {
    String visitDot(Dot dot);
    String visitCircle(Circle circle);
    String visitRectangle(Rectangle rectangle);
    String visitCompoundGraphic(CompoundShape cg);
}
```
```java
public class XMLExportVisitor implements Visitor {

    public String export(Shape... args) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "\n");
        for (Shape shape : args) {
            sb.append(shape.accept(this)).append("\n");
        }
        return sb.toString();
    }

    public String visitDot(Dot d) {
        return "<dot>" + "\n" +
                "    <id>" + d.getId() + "</id>" + "\n" +
                "    <x>" + d.getX() + "</x>" + "\n" +
                "    <y>" + d.getY() + "</y>" + "\n" +
                "</dot>";
    }

    public String visitCircle(Circle c) {
        return "<circle>" + "\n" +
                "    <id>" + c.getId() + "</id>" + "\n" +
                "    <x>" + c.getX() + "</x>" + "\n" +
                "    <y>" + c.getY() + "</y>" + "\n" +
                "    <radius>" + c.getRadius() + "</radius>" + "\n" +
                "</circle>";
    }

    public String visitRectangle(Rectangle r) {
        return "<rectangle>" + "\n" +
                "    <id>" + r.getId() + "</id>" + "\n" +
                "    <x>" + r.getX() + "</x>" + "\n" +
                "    <y>" + r.getY() + "</y>" + "\n" +
                "    <width>" + r.getWidth() + "</width>" + "\n" +
                "    <height>" + r.getHeight() + "</height>" + "\n" +
                "</rectangle>";
    }

    public String visitCompoundGraphic(CompoundShape cg) {
        return "<compound_graphic>" + "\n" +
                "   <id>" + cg.getId() + "</id>" + "\n" +
                    visitInnerCompoundGraphic(cg) +
                "</compound_graphic>";
    }

    private String visitInnerCompoundGraphic(CompoundShape cg) {
        StringBuilder sb = new StringBuilder();
        for (Shape shape : cg.children) {
            String obj = shape.accept(this);
            
            obj = "    " + obj.replace("\n", "\n    ") + "\n";
            sb.append(obj);
        }
        return sb.toString();
    }

}
```
`XMLExportVisitor`를 구현하여 `XML`에 형식에 맞게 도형을 출력할 수 있다. 

만약 `JSON` 형태로 출력하고 싶다면 기본 구조는 그대로 두고 `JsonExportVisitor`를 새로 만들어서 기능을 추가할 수 있다. 

### 실행
```java
import behavior.visitor.concrete_visitor.XMLExportVisitor;
import behavior.visitor.element.Rectangle;

public class Client {
    public static void main(String[] args) {
        Dot dot = new Dot(1, 10, 55);
        Circle circle = new Circle(2, 23, 15, 10);
        Rectangle rectangle = new Rectangle(3, 10, 17, 20, 30);

        CompoundShape compoundShape = new CompoundShape(4);
        compoundShape.add(dot);
        compoundShape.add(circle);
        compoundShape.add(rectangle);

        CompoundShape c = new CompoundShape(5);
        c.add(dot);
        compoundShape.add(c);

        System.out.println(new XMLExportVisitor().export(circle, compoundShape));
    }
}
```
위 코드를 실행하면 아래와 같은 결과를 얻을 수 있게 된다. 
```xml
<?xml version="1.0" encoding="utf-8"?>
<circle>
    <id>2</id>
    <x>23</x>
    <y>15</y>
    <radius>10</radius>
</circle>

<?xml version="1.0" encoding="utf-8"?>
<compound_graphic>
   <id>4</id>
    <dot>
        <id>1</id>
        <x>10</x>
        <y>55</y>
    </dot>
    <circle>
        <id>2</id>
        <x>23</x>
        <y>15</y>
        <radius>10</radius>
    </circle>
    <rectangle>
        <id>3</id>
        <x>10</x>
        <y>17</y>
        <width>20</width>
        <height>30</height>
    </rectangle>
    <compound_graphic>
       <id>5</id>
        <dot>
            <id>1</id>
            <x>10</x>
            <y>55</y>
        </dot>
    </compound_graphic>
</compound_graphic>
```