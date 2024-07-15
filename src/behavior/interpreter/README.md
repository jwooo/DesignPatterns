# INTERPRETER PATTERN
## 인터프리터 패턴이란? 
- 인터프리터 패턴은 자주 등장하는 문제를 별개의 언어로 정의하고 재사용하는 패턴이다. 
  - 문법에 등장하는 규칙을 클래스로 표현하고 언어에서의 표현식을 해석하고 평가한다.
  - `Expression`이라는 추상 클래스를 만드는 경우가 많다. 
  - `Expression` 클래스에서 파생된 구체적인 클래스는 언어의 다양한 규칙 또는 요소를 나타낸다. 
- 반복되는 문제 패턴을 언어 또는 문법으로 정의하고 확장할 수 있다. 

## 인터프리터 패턴 구조 
![](https://cdn.jsdelivr.net/gh/n00nietzsche/posting_cdn@main/images/pic_1682689324324_1682689325450.png)
### Context
- 모든 `Expression`에서 사용하는 공통 정보
### Expression
- 표현하는 문법을 나타냄
### TerminalExpression
- 종료되는 `Expression`
### NonTerminalExpression
- 다른 `Expression`을 재귀적으로 참조하고 있는 `Expression`

## 인터프리터 패턴 특징
### 사용 시기
- 간단한 언어를 구현할 때 사용한다. 
- 문법이 간단하며, 효율보다는 단순하게 만드는 것이 더 중요한 경우에 유용하다.

### 장점
- 스크립트 언어 및 프로그래밍 언어에서 모두 사용할 수 있다. 
- 문법의 추가 및 수정, 구현이 쉬워지게 된다. 
### 단점
- 문법 규칙의 개수가 많아지면서 복잡도가 증가한다. 
- 복잡한 문법의 경우 관리 및 유지가 어려워진다. 

## 인터프리터 패턴 예시
인터프리터 패턴으로 후위 표기법을 구현해보자. 

후위 표기법은 `AB+`의 형태로 피연산자를 먼저 표시하고 연산자를 나중에 표시하는 방법이다.
예를 들어 `13+`는 우리가 알고있는 1더하기 3으로 표시할 수 있다. 

```java
public interface Expression {
  int interpret(Map<Character, Integer> context);
}
```
후위 표기법의 표현 문법을 나타낼 `Expression` 인터페이스를 생성한다. 
`AB+`의 형태의 `13+`를 대입한 것 처럼 `Map<Character, Integer>` 데이터 구조를 활용하여 `A`가 `1`임을 전달한다. 

```java
public class PlusExpression implements Expression {
  
    private Expression left;
    private Expression right;
  
    public PlusExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
  
    @Override
    public int interpret(Map<Character, Integer> context) {
        return left.interpret(context) + right.interpret(context);
    }
}
```
```java
public class MinusExpression implements Expression {
    
    private Expression left;
    private Expression right;
  
    public MinusExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
  
    @Override
    public int interpret(Map<Character, Integer> context) {
        return left.interpret(context) - right.interpret(context);
    }
}
```
`Expression` 인터페이스를 상속 받은 `NonTerminalExpression`인 `PlusExpression`, `MinusExpression` 구상 클래스는 `interpret` 메서드를
구현한다. 

```java
public class VariableExpression implements Expression {
    
    private Character character;
  
    public VariableExpression(Character character) {
        this.character = character;
    }
  
    @Override
    public int interpret(Map<Character, Integer> context) {
        return context.get(this.character);
    }
}
```
`TerminalExpression`인 `VariableExpression` 클래스는 값을 그대로 반환하는 종료 `Expression`이다.

```java
import behavior.interpreter.expression.Expression;
import java.util.Stack;

public class PostfixParser {

  public static Expression parse(String expression) {
    Stack<Expression> stack = new Stack<>();

    for (char c : expression.toCharArray()) {
      stack.push(getExpression(c, stack));
    }

    return stack.pop();
  }

  private static Expression getExpression(char c, Stack<Expression> stack) {
    switch (c) {
      case '+':
        return new PlusExpression(stack.pop(), stack.pop());
      case '-':
        Expression right = stack.pop();
        Expression left = stack.pop();

        return new MinusExpression(left, right);
      default:
        return new VariableExpression(c);
    }
  }
}
```
후위 표기법으의 계산 방식대로 파싱해주는 `PostfixParser` 클래스는 `parse()` 메서드로 받은 문자열을 `character`로 
하나씩 쪼갠 뒤 `getExpression()` 메서드로 `stack`에 넣는다. 

```java
public class Client {
  public static void main(String[] args) {
    Expression expression = PostfixParser.parse("xyz+-");
    int result = expression.interpret(Map.of('x', 1, 'y', 2, 'z', 3));
    System.out.println(result);
  }
}
```
`xyz+-`는 `z+y-x` 이므로 실행하면 `-4`라는 결과가 나온다. 