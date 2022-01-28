package expression;


abstract class AbstractExpression implements Expression, TripleExpression {
    final TripleExpression[] expressions;
    String rule;


    AbstractExpression(TripleExpression exp1, TripleExpression exp2, String rule) {
        this.expressions = new TripleExpression[2];
        expressions[0] = exp1;
        expressions[1] = exp2;
        this.rule = rule;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int calc1 = expressions[0].evaluate(x, y, z);
        int calc2 = expressions[1].evaluate(x, y, z);
        return calculate(calc1, calc2);
    }

    abstract int calculate(int x, int y);

    @Override
    public int evaluate(int x) {
        Expression exp1 = (Expression) expressions[0];
        Expression exp2 = (Expression) expressions[1];
        int calc1 = exp1.evaluate(x);
        int calc2 = exp2.evaluate(x);
        return calculate(calc1, calc2);
    }

    @Override
    public String toString() {
        return "(" + expressions[0].toString() + " " + rule + " " + expressions[1].toString() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        AbstractExpression newExpr = (AbstractExpression) obj;
        return this.expressions[0].equals(newExpr.expressions[0]) && this.expressions[1].equals(newExpr.expressions[1]);
    }

    @Override
    public int hashCode() {
        return ((31 * expressions[0].hashCode() + expressions[1].hashCode()) * 31 + getClass().hashCode()) * 31;
    }

}
