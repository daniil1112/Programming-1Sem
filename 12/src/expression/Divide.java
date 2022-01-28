package expression;

public class Divide extends AbstractExpression {
    public Divide(TripleExpression exp1, TripleExpression exp2) {
        super(exp1, exp2, "/");
    }

    public int calculate(int x, int y) {
        return x / y;
    }
}
