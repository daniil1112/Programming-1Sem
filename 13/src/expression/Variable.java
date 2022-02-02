package expression;

public class Variable implements Expression, TripleExpression {

    String name;

    public Variable(String x) {
        name = x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        switch (name) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                throw new IllegalArgumentException("Variable with name " + name + "not found");
        }
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        return name.equals(obj.toString());
    }

    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
