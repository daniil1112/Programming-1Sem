package expression.parser;

import expression.*;

public class ExpressionParser implements Parser {

    public TripleExpression parse(final String string) {
        return parse(new StringCharSource(string));
    }

    public TripleExpression parse(final StringCharSource source) {
        return new CalculatorParser(source).parse();
    }


    private static class CalculatorParser extends BaseParser {

        public enum operations {
            MIN, MAX, ADD, CONST, DIVIDE, MUL, SUB, UNARSUB, VAR, ERR, BRACKET
        }

        protected operations last = operations.ERR;



        public CalculatorParser(final StringCharSource source) {
            super(source);
        }


        public TripleExpression parse() {
            skipWSP();
            TripleExpression res = parseExpr();
            skipWSP();
            if (!isEnd()){
                throw new RuntimeException("Error in expression");
            }
            return res;
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
                    TripleExpression right = startParsePriority(priority+1);


                    switch (operation){
                        case ADD -> left = new Add(left, right);
                        case SUB -> left = new Subtract(left, right);
                        case MUL -> left = new Multiply(left, right);
                        case DIVIDE -> left = new Divide(left, right);
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
                    if (test('*')){
                        return operations.MUL;
                    }
                    if (test('*')){
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
                    throw new RuntimeException("Two vars/const in line");
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
                return new UnarSub(parseExpr());
            }
            if (between('x', 'z')){
                if (last == operations.VAR || last == operations.CONST) {
                    throw new RuntimeException("Two vars/const in line");
                }
                last = operations.VAR;
                return new Variable(String.valueOf(take()));
            }

            if (take('(')){
                if (last == operations.VAR || last == operations.CONST) {
                    throw new RuntimeException("Var and bracket in line");
                }
                last = operations.BRACKET;

                TripleExpression expression = parseExpr();
                if (!take(')')){
                    throw new RuntimeException("Missing close bracket");
                }
                return expression;
            }

            throw new RuntimeException("Неизвестная ошибочка "+getChar());

        }



        private int parseConst(boolean isUnarParse) {
            StringBuilder res = new StringBuilder();
            if (isUnarParse) {
                res.append("-");
            }
            while (between('0', '9')) {
                res.append(take());
            }
            return Integer.parseInt(res.toString());
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
