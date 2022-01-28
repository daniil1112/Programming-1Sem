package expression;

public class Const implements Expression{
	final int value;
	public Const(int x){
		value = x;
	}

	public int evaluate(int x){
		return value;
	}

	@Override
	public String toString(){
		return String.valueOf(value);
	}

	@Override
	public boolean equals(Object obj){
		if (obj == null || obj.getClass() != this.getClass()){
			return false;
		}
		return this.toString().equals(obj.toString());
	}

	@Override
	public int hashCode(){
		return value;
	}

}