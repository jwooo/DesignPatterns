package behavior.interpreter.expression;

import java.util.Map;

public interface Expression {
    int interpret(Map<Character, Integer> context);

}
