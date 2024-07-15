package behavior.interpreter.parser;

import behavior.interpreter.expression.Expression;
import behavior.interpreter.expression.MinusExpression;
import behavior.interpreter.expression.PlusExpression;
import behavior.interpreter.expression.VariableExpression;
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
            case '+' :
                return new PlusExpression(stack.pop(), stack.pop());
            case '-' :
                Expression right = stack.pop();
                Expression left = stack.pop();

                return new MinusExpression(left, right);
            default:
                return new VariableExpression(c);
        }
    }

}
