package expression.exceptions;

import expression.Expression;
import expression.TripleExpression;

public abstract class AbstractChecked implements Expression, TripleExpression {
    private final TripleExpression expression1, expression2;
    private final String operation;

    public AbstractChecked(TripleExpression expression1, TripleExpression expression2, String operation) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    protected abstract void checkOverflow(int val1, int val2);

    protected abstract int calculate(int val1, int val2);


    public int evaluate(int x) {
        Expression exp1 = (Expression) expression1;
        Expression exp2 = (Expression) expression2;
        int val1 = exp1.evaluate(x);
        int val2 = exp2.evaluate(x);
        checkOverflow(val1, val2);
        return calculate(val1, val2);
    }

    public int evaluate(int x, int y, int z) {
        int val1 = expression1.evaluate(x, y, z);
        int val2 = expression2.evaluate(x, y, z);
        checkOverflow(val1, val2);
        return calculate(val1, val2);
    }

    public String toString() {
        return "(" + expression1 + " " + operation + " " + expression2 + ")";
    }

}
