package expression;

public class Max extends AbstractExpression {
    public Max(TripleExpression exp1, TripleExpression exp2) {
        super(exp1, exp2, "max");
    }

    public int calculate(int x, int y) {
        return Math.max(x, y);
    }
}
