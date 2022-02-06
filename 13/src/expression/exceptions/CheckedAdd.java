package expression.exceptions;

import expression.TripleExpression;

public class CheckedAdd extends AbstractChecked {
    public CheckedAdd(TripleExpression exp1, TripleExpression exp2) {
        super(exp1, exp2, "+");
    }

    @Override
    protected void checkOverflow(int val1, int val2) {
        if (val1 > 0 && val2 > 0 && val1 + val2 < val1 || val1 < 0 && val2 < 0 && val1 + val2 > val1) {
            throw new OverflowException("Add overflow");
        }
    }

    @Override
    protected int calculate(int val1, int val2) {
        return val1 + val2;
    }

}
