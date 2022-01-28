package expression;

public class Variable implements Expression{
	final String name;
	public Variable(String name){
		this.name = name;
	}

	public int evaluate(int x){
		return x;
	}

	@Override
	public String toString(){
		return name;
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
		return name.hashCode();
	}

}