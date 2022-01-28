package expression;

public class UnarSub implements TripleExpression, Expression {

    TripleExpression expression;

    public UnarSub(TripleExpression expression) {
        this.expression = expression;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -expression.evaluate(x, y, z);
    }

    @Override
    public int evaluate(int x) {
        return -((Expression) expression).evaluate(x);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        return (toString()).equals(obj.toString());
    }

    public String toString() {
        return "-(" + expression.toString() + ")";
    }

    @Override
    public int hashCode() {
        return (toString()).hashCode();
    }

}
