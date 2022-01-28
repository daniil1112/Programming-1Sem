package expression;

public class Min extends AbstractExpression {
    public Min(TripleExpression exp1, TripleExpression exp2) {
        super(exp1, exp2, "min");
    }

    public int calculate(int x, int y) {
        return Math.min(x, y);
    }
}
