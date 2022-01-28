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

        private char operation;
        private int constant_el;
        private String variable_el;

        public CalculatorParser(final StringCharSource source) {
            super(source);
        }

        public TripleExpression parse() {
            return parseMinMax();
        }

        private TripleExpression parseMinMax() {
            TripleExpression left = parseAddSub();
            TripleExpression right;
            while (true) {
                switch (operation) {
                    case 'h':
                        right = parseAddSub();
                        left = new Max(left, right);
                        break;
                    case 'l':
                        right = parseAddSub();
                        left = new Min(left, right);
                        break;
                    default:
                        return left;
                }
            }
        }

        private TripleExpression parseAddSub() {
            TripleExpression left = parseMulDiv();
            TripleExpression right;
            while (true) {
                switch (operation) {
                    case '+':
                        right = parseMulDiv();
                        left = new Add(left, right);
                        break;
                    case '-':
                        right = parseMulDiv();
                        left = new Subtract(left, right);
                        break;
                    default:
                        return left;
                }
            }
        }

        private TripleExpression parseMulDiv() {
            TripleExpression left = parseEl();
            TripleExpression right;
            while (true) {
                switch (operation) {
                    case '*':
                        right = parseEl();
                        left = new Multiply(left, right);
                        break;
                    case '/':
                        right = parseEl();
                        left = new Divide(left, right);
                        break;
                    default:
                        return left;
                }
            }
        }

        private TripleExpression parseEl() {
            parseValConstOper(true);
            TripleExpression save;
            switch (operation) {
                case '(':
                    save = parseMinMax();
                    parseValConstOper(false);
                    return save;
                case '-':
                    return new UnarSub(parseEl());
                case '1':
                    save = new Const(constant_el);
                    parseValConstOper(false);
                    return save;
                case '2':
                    save = new Variable(variable_el);
                    parseValConstOper(false);
                    return save;
                default:
                    throw new ArithmeticException("Expression has no supported type");

            }
        }

        private void parseValConstOper(boolean canunar) {
            skipWSP();
            if (take('(')) {
                operation = '(';
            } else if (take(')')) {
                operation = ')';
            } else if (take('+')) {
                operation = '+';
            } else if (take('*')) {
                operation = '*';
            } else if (take('/')) {
                operation = '/';
            } else if (testWord("max")) {
                operation = 'h';
            } else if (testWord("min")) {
                operation = 'l';
            } else if (take('-')) {
                if (!canunar || !between('0', '9')) {
                    operation = '-';
                } else {
                    operation = '1';
                    parseConst(true);
                }
            } else if (between('x', 'z')) {
                operation = '2';
                parseVar();
            } else if (between('0', '9')) {
                operation = '1';
                parseConst(false);
            }
        }


        private void parseConst(boolean isUnarParse) {
            StringBuilder res = new StringBuilder();
            if (isUnarParse) {
                res.append("-");
            }
            while (between('0', '9')) {
                res.append(take());
            }
            constant_el = Integer.parseInt(res.toString());
        }

        private void parseVar() {
            StringBuilder res = new StringBuilder();
            while (between('x', 'z')) {
                res.append(take());
            }
            if (res.length() > 1) {
                throw new ArithmeticException("Variable has no supported type");
            }
            variable_el = res.toString();
        }


        protected void skipWSP() {
            while (!end()) {
                if (!Character.isWhitespace(getCurrentChar())) {
                    break;
                }
                take();
            }
        }

    }
}
