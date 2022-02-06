package expression.exceptions;

import expression.TripleExpression;

public class CheckedDivide extends AbstractChecked {

    public CheckedDivide(TripleExpression exp1, TripleExpression exp2) {
        super(exp1, exp2, "/");
    }

    @Override
    protected void checkOverflow(int val1, int val2) {
        if (val2 == 0) {
            throw new DivisionOnZero("Error div on zero");
        }
        if (val1 == Integer.MIN_VALUE && val2 == -1) {
            throw new OverflowException("Div overflow");
        }
    }

    @Override
    protected int calculate(int val1, int val2) {
        return val1 / val2;
    }


}
