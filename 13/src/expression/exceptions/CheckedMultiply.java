package expression.exceptions;


import expression.TripleExpression;


public class CheckedMultiply extends AbstractChecked {

    public CheckedMultiply(TripleExpression exp1, TripleExpression exp2) {
        super(exp1, exp2, "*");
    }

    @Override
    protected void checkOverflow(int val1, int val2) {
        if (val1 != 0 && val2 != 0 && ((val1 * val2) / val2 != val1 || (val1 == Integer.MIN_VALUE && val2 < 0) || (val1 < 0 && val2 == Integer.MIN_VALUE))) {
            throw new OverflowException("Mul overflow");
        }
    }

    @Override
    protected int calculate(int val1, int val2) {
        return val1 * val2;
    }


}
