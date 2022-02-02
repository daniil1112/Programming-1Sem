package common;

import base.ExtendedRandom;
import base.TestCounter;
import expression.common.FullRenderer;
import expression.common.Generator;
import expression.common.MiniRenderer;
import expression.common.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class TestGenerator<C> {
    private final TestCounter counter;
    private final ExtendedRandom random;

    private final expression.common.Generator<C> generator;
    private final expression.common.FullRenderer<C> full = new FullRenderer<>();
    private final expression.common.MiniRenderer<C> mini = new expression.common.MiniRenderer<>(false);
    private final expression.common.MiniRenderer<C> safe = new MiniRenderer<>(true);

    private final List<expression.common.Node<C>> args;
    private final List<expression.common.Node<C>> basicTests;
    private final List<expression.common.Node<C>> variables = new ArrayList<>();
    private final List<expression.common.Node<C>> consts;
    private final boolean verbose;

    public TestGenerator(
            final TestCounter counter,
            final ExtendedRandom random,
            final Supplier<C> constant,
            final List<C> constants,
            final boolean verbose
    ) {
        this.counter = counter;
        this.random = random;
        this.verbose = verbose;

        generator = new Generator<>(random, constant);
        full.unary("(", a -> "(" + a + ")");

        consts = constants.stream().map(expression.common.Node::constant).collect(Collectors.toUnmodifiableList());
        args = new ArrayList<>(consts);
        basicTests = new ArrayList<>(consts);
    }

    private void test(final expression.common.Node<C> node, final Consumer<Test<C>> consumer) {
        consumer.accept(new Test<>(full.render(node), mini.render(node), safe.render(node), node));
    }

    private expression.common.Node<C> c() {
        return random.randomItem(consts);
    }

    private expression.common.Node<C> v() {
        return random.randomItem(variables);
    }

    @SafeVarargs
    private static <C> expression.common.Node<C> f(final String name, final expression.common.Node<C>... arg) {
        return expression.common.Node.op(name, arg);
    }

    @SafeVarargs
    private void basicTests(final expression.common.Node<C>... tests) {
        basicTests.addAll(Arrays.asList(tests));
    }

    public void variable(final String name) {
        generator.add(name, 0);
        full.nullary(name);
        mini.nullary(name);
        safe.nullary(name);
        basicTests(f(name));
        args.add(f(name));
        variables.add(f(name));
    }

    public void unary(final String name) {
        generator.add(name, 1);
        full.unary(name);
        mini.unary(name);
        safe.unary(name);

        if (verbose) {
            args.stream().map(a -> f(name, a)).forEachOrdered(basicTests::add);
        } else {
            basicTests(
                    f(name, c()),
                    f(name, v())
            );
        }

        final expression.common.Node<C> p1 = f(name, f(name, f("+", v(), c())));
        final expression.common.Node<C> p2 = f("*", v(), f("*", v(), f(name, c())));
        basicTests(
                f(name, f("+", v(), v())),
                f(name, f(name, v())),
                f(name, f("/", f(name, v()), f("+", v(), v()))),
                p1,
                p2,
                f("+", p1, p2)
        );
    }

    public void binary(final String name, final int priority) {
        generator.add(name, 2);
        full.binary(name);
        mini.binary(name, priority);
        safe.binary(name, priority);

        if (verbose) {
            args.stream().flatMap(a -> args.stream().map(b -> f(name, a, b))).forEachOrdered(basicTests::add);
        } else {
            basicTests(
                    f(name, c(), c()),
                    f(name, v(), c()),
                    f(name, c(), v()),
                    f(name, v(), v())
            );
        }

        final expression.common.Node<C> p1 = f(name, f(name, f("+", v(), c()), v()), v());
        final expression.common.Node<C> p2 = f("*", v(), f("*", v(), f(name, c(), v())));

        basicTests(
                f(name, f(name, v(), v()), v()),
                f(name, v(), f(name, v(), v())),
                f(name, f(name, v(), v()), f(name, v(), v())),
                f(name, f("-", f(name, v(), v()), c()), f("+", v(), v())),
                p1,
                p2,
                f("+", p1, p2)
        );
    }

    public void testBasic(final Consumer<Test<C>> consumer) {
        basicTests.forEach(test -> {
            counter.println(full.render(test));
            test(test, consumer);
        });
    }

    public void testRandom(final int denominator, final Consumer<Test<C>> consumer) {
        generator.testRandom(denominator, counter, node -> test(node, consumer));
    }

    public String full(final expression.common.Node<C> node) {
        return full.render(node);
    }

    public static class Test<C> {
        public final String full;
        public final String mini;
        public final String safe;
        public final expression.common.Node<C> node;

        public Test(final String full, final String mini, final String safe, final Node<C> node) {
            this.full = full;
            this.mini = mini;
            this.safe = safe;
            this.node = node;
        }
    }
}
