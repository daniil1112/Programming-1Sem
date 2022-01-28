package expression;

public class Add extends AbstractExpression{
	public Add(Expression exp1, Expression exp2){
		super(exp1, exp2, '+');
	}

	public int evaluate(int x){
		return expressions[0].evaluate(x) + expressions[1].evaluate(x);
	}

}