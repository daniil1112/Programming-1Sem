package common;

import expression.common.Node;
import expression.common.Renderer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class MiniRenderer<C> {
    private static final String PAREN = "(";
    private static final String SPACE = "_";

    private final boolean safe;
    private final expression.common.Renderer<C, expression.common.Node<C>> nodeRenderer = new expression.common.Renderer<>(expression.common.Node::constant);
    private final expression.common.Renderer<C, String> stringRenderer = new Renderer<>(String::valueOf);
    private final Map<String, Priority> priorities = new HashMap<>();

    public MiniRenderer(final boolean safe) {
        this.safe = safe;
        nodeRenderer.unary("(", arg -> expression.common.Node.op(PAREN, arg));
        stringRenderer.unary("(", arg -> "(" + arg + ")");
        stringRenderer.unary("_", " "::concat);
    }

    public void nullary(final String name) {
        nodeRenderer.nullary(name, expression.common.Node.op(name));
        stringRenderer.nullary(name, name);
    }

    public void unary(final String name) {
        nodeRenderer.unary(name, a -> expression.common.Node.op(name, a.<expression.common.Node<C>>get(
                c -> expression.common.Node.op(SPACE, expression.common.Node.constant(c)),
                (n, c) -> expression.common.Node.op(c.size() > 1 ? PAREN : SPACE, expression.common.Node.op(n, c))
        )));
        stringRenderer.unary(name, name::concat);
    }

    public void unary(final String name, final UnaryOperator<String> op) {
        nodeRenderer.unary(name, UnaryOperator.identity());
        stringRenderer.unary(name, op);
    }

    private Priority priority(final String name) {
        return priorities.getOrDefault(name, Priority.MAX);
    }

    public void binary(final String name, final int priority) {
        final Priority mp = new Priority(priority, safe);
        priorities.put(expression.common.Node.id(name, 2), mp);

        nodeRenderer.binary(name, (l, r) -> {
            final Priority lp = l.get(c -> Priority.MAX, (n, c) -> priority(n));
            final Priority rp = r.get(c -> Priority.MAX, (n, c) -> priority(n));
            final expression.common.Node<C> ra = mp.right(rp) ? expression.common.Node.op(PAREN, r) : r;
            final int lc = mp.left(lp);
            if (lc < 0) {
                return expression.common.Node.op(name, expression.common.Node.op(PAREN, l), ra);
            } else if (lc > 0 || !safe) {
                return expression.common.Node.op(name, l, ra);
            } else {
                return l.get(
                        c -> expression.common.Node.op(name, l, ra),
                        (n, children) -> children.size() == 2
                                ? expression.common.Node.op(n, List.of(children.get(0), expression.common.Node.op(name, children.get(1), ra)))
                                : expression.common.Node.op(name, l, ra)
                );
            }
        });

        stringRenderer.binary(name, (a, b) -> a + " " + name + " " + b);
    }

    public String render(final Node<C> test) {
        return stringRenderer.render(nodeRenderer.render(test));
    }

    // :NOTE: Especially ugly bit-fiddling, do not replicate
    private static final class Priority {
        private static final Priority MAX = new Priority(Integer.MAX_VALUE, true);

        private final int priority;
        private final int abs;
        private final boolean safe;

        public Priority(final int priority, final boolean safe) {
            this.priority = priority;
            abs = Math.abs(priority);
            this.safe = safe;
        }

        private int left(final Priority l) {
            return (l.abs | 1) - (abs | 1);
        }

        private boolean right(final Priority r) {
            return (r.abs | 1) <=
                    (safe && (priority < 0 || (abs & 1) == 1) ? abs + 1 : priority < 0 ? abs | 1 : abs & ~(r.abs & 1));
        }
    }
}
