package expression.exceptions;


import expression.TripleExpression;

public class CheckedSubtract extends AbstractChecked {
    public CheckedSubtract(TripleExpression exp1, TripleExpression exp2) {
        super(exp1, exp2, "-");
    }

    @Override
    protected void checkOverflow(int val1, int val2) {
        if (val1 >= 0 && val2 <= 0 && val1 - val2 < val1) {
            throw new OverflowException("Sub overflow");
        }
        if (val1 <= 0 && val2 >= 0 && val1 - val2 > val1) {
            throw new OverflowException("Sub overflow");
        }
    }

    @Override
    protected int calculate(int val1, int val2) {
        return val1 - val2;
    }


}
