package expression.exceptions;

import expression.TripleExpression;
import expression.Expression;

public class CheckedDivide implements Expression, TripleExpression{
    private final TripleExpression expression1, expression2;

    public CheckedDivide(TripleExpression exp1, TripleExpression exp2) {
        this.expression1 = exp1;
        this.expression2 = exp2;
    }

    private void checkOverflow(int val1, int val2){
        if (val2 == 0){
            throw new DivisionOnZero("Error div on zero");
        }
        if (val1 == Integer.MIN_VALUE && val2 == -1){
            throw new OverflowException("Div overflow");
        }
    }

    @Override
    public int evaluate(int x) {
        Expression exp1 = (Expression) expression1;
        Expression exp2 = (Expression) expression2;
        int val1 = exp1.evaluate(x);
        int val2 = exp2.evaluate(x);
        checkOverflow(val1, val2);
        return val1/val2;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int val1 = expression1.evaluate(x, y, z);
        int val2 = expression2.evaluate(x, y, z);
        checkOverflow(val1, val2);
        return val1/val2;
    }

    @Override
    public String toString() {
        return "("+expression1+" / "+expression2+")";
    }
}
