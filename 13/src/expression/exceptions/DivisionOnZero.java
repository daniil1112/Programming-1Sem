package expression.exceptions;

public class DivisionOnZero extends ArithmeticException{
    public DivisionOnZero(String message) {
        super(message);
    }
}
