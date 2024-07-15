package behavior.interpreter.expression;

import behavior.interpreter.expression.Expression;
import java.util.Map;

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
