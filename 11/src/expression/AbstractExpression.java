package expression;

abstract class AbstractExpression implements Expression{

	final Expression[] expressions;
	final char rule;

	public AbstractExpression(Expression exp1, Expression exp2, char rule){
		expressions = new Expression[2];
		expressions[0] = exp1;
		expressions[1] = exp2;
		this.rule = rule;
	}

	
	@Override
	public boolean equals(Object obj){
		if (obj == null || obj.getClass() != this.getClass()){
			return false;
		}
		AbstractExpression newExpr = (AbstractExpression) obj;
		return expressions[0].equals(newExpr.expressions[0]) && expressions[1].equals(newExpr.expressions[1]);
	}

	@Override
	public String toString(){
		return "("+expressions[0]+" "+rule+" "+expressions[1]+")";
	}

	@Override
	public int hashCode(){
		return ((30* expressions[0].hashCode() + expressions[1].hashCode())*24 + this.getClass().hashCode())*21;
	}
	
}