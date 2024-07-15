package behavior.interpreter;

import behavior.interpreter.expression.Expression;
import behavior.interpreter.parser.PostfixParser;
import java.util.Map;

public class Client {
    public static void main(String[] args) {
        Expression expression = PostfixParser.parse("xyz+-");
        int result = expression.interpret(Map.of('x', 1, 'y', 2, 'z', 3));

        System.out.println(result);
    }
}
