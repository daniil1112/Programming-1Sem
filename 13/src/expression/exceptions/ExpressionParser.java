package expression.exceptions;

import expression.*;
import expression.parser.BaseParser;
import expression.parser.StringCharSource;

public class ExpressionParser implements Parser {

    public TripleExpression parse(final String string) {
        return parse(new expression.parser.StringCharSource(string));
    }

    public TripleExpression parse(final expression.parser.StringCharSource source) {
        return new CalculatorParser(source).parse();
    }


    private static class CalculatorParser extends BaseParser {

        public enum operations {
            MIN, MAX, ADD, CONST, DIVIDE, MUL, SUB, UNARSUB, VAR, ERR, BRACKET, SIGN, START
        }

        protected operations last = operations.START;



        public CalculatorParser(final StringCharSource source) {
            super(source);
        }


        public TripleExpression parse() {
            skipWSP();
            TripleExpression res = parseExpr();
            skipWSP();
            if (!isEnd()){
                checkBrackets();
                checkErrorSign();
                checkErrorSpace();
                throw new ParsingException("Waited end of expression, got:'"+getChar()+"'");
            }
            return res;
        }

        private void checkBrackets(){
            if (test('(')){
                throw new BracketsException("No closing parenthesis");
            }
            if (test(')')){
                throw new BracketsException("No opening parenthesis");
            }
        }
        private void checkErrorSign(){
            if (!between('x','z') && !testWord("min") && !testWord("max") && !between('0','9') && !test('-')
            &&!test('+') && !test('*') && !test('/')){
                throw new IllegalSignException("Illegal sign");
            }
        }
        private void checkErrorSpace(){
            if (between('x','z')||between('0','9')){
                throw new SignException("Expected sign between two arguments");
            }
        }

        private TripleExpression parseExpr(){
            return startParsePriority(1);
        }

        private TripleExpression startParsePriority(int priority){
            if (priority < 3){
                TripleExpression left = startParsePriority(priority+1);


                while (true){
                    operations operation = parseOperation(priority);


                    if (operation == operations.ERR){
                        return left;
                    }

                    last = operations.SIGN;

                    TripleExpression right = startParsePriority(priority+1);


                    switch (operation){
                        case ADD -> left = new CheckedAdd(left, right);
                        case SUB -> left = new CheckedSubtract(left, right);
                        case MUL -> left = new CheckedMultiply(left, right);
                        case DIVIDE -> left = new CheckedDivide(left, right);
                        case MIN -> left = new Min(left, right);
                        case MAX -> left = new Max(left, right);
                    }


                }
            } else {
                return parseEL();
            }
        }


        private operations parseOperation(int priority){
            skipWSP();
            switch (priority){
                case 1:
                    if (take('+')){
                        return operations.ADD;
                    }
                    if (take('-')){
                        return operations.SUB;
                    }
                    if (testWord("min")){
                        return operations.MIN;
                    }
                    if (testWord("max")){
                        return operations.MAX;
                    }
                case 2:
                    if (take('*')){
                        return operations.MUL;
                    }
                    if (take('/')){
                        return operations.DIVIDE;
                    }
                default:
                    return operations.ERR;
            }
        }

        private TripleExpression parseEL(){
            skipWSP();

            if (between('0', '9')){
                if (last == operations.VAR || last == operations.CONST) {
                    throw new ArgumentException("Two vars/const in line");
                }
                last = operations.CONST;
                return new Const(parseConst(false));
            }
            if (take('-')){
                if (between('0', '9')){
                    last = operations.CONST;
                    return new Const(parseConst(true));
                }
                last = operations.UNARSUB;
                return new CheckedNegate(startParsePriority(3));
            }
            if (between('x', 'z')){
                if (last == operations.VAR || last == operations.CONST) {
                    throw new ArgumentException("Two vars/const in line");
                }
                last = operations.VAR;
                return new Variable(String.valueOf(take()));
            }

            if (take('(')){
                if (last == operations.VAR || last == operations.CONST) {
                    throw new ArgumentException("Var and bracket in line");
                }
                last = operations.BRACKET;

                if (test(')')){
                    throw new BracketsException("Bracket close after open");
                }

                TripleExpression expression = parseExpr();
                if (!take(')')){
                    checkErrorSign();
                    throw new BracketsException("Missing close bracket");
                }
                return expression;
            }

            if (take(')')){
                throw new BracketsException("Missing opening bracket");
            }

            if (take('+') || take('*') || take('/')|| testWord("min") || testWord("max")){
                if (last == operations.SIGN){
                    throw new ArgumentException("No second argument");
                }
                throw new ArgumentException("No first argument");
            }


            if (last == operations.SIGN){
                throw new ArgumentException("No second argument");
            }


            if (last == operations.UNARSUB){
                throw new ArgumentException("No argument to negate");
            }

            if (last == operations.START){
                throw new ArgumentException("Illegal start argument");
            }

            if (last == operations.VAR){
                throw new SpacesExceptions("Error with spaces btw numbers");
            }



            throw new ParsingException("Error while parsing");

        }



        private int parseConst(boolean isUnarParse) {
            StringBuilder res = new StringBuilder();
            if (isUnarParse) {
                res.append("-");
            }
            while (between('0', '9')) {
                res.append(take());
            }
            if (testWord("min") || testWord("max")){
                throw new SpacesExceptions("Operation need spaces");
            }
            try {
                return Integer.parseInt(res.toString());
            } catch (NumberFormatException e){
                throw new SpacesExceptions("Error in constant");
            }

        }



        protected void skipWSP() {
            while (!end()) {
                if (!Character.isWhitespace(getChar())) {
                    break;
                }
                take();
            }
        }

    }
}
