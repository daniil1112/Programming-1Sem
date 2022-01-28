package base;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class TestCounter extends Log {
    public static final int DENOMINATOR = Integer.parseInt(Objects.requireNonNullElse(System.getProperty("base.denominator"), "1"));

    private final long start = System.currentTimeMillis();
    private int passed;

    public int getTestNo() {
        return passed + 1;
    }

    public void test(final Runnable action) {
        testV(() -> {
            action.run();
            return null;
        });
    }

    public <T> void testForEach(final Iterable<? extends T> items, final Consumer<? super T> action) {
        for (final T item : items) {
            test(() -> action.accept(item));
        }
    }

    public <T> T testV(final Supplier<T> action) {
        return silentScope("Test " + getTestNo(), () -> {
            final T result = action.get();
            passed++;
            return result;
        });
    }

    public void test(final Class<?> testClass, final Map<String, Object> properties, final Runnable action) {
        scope(getLine().repeat(3) + " " + title(testClass, properties), action);
        printStatus(testClass, properties);
    }

    private String getLine() {
        return getIndent() == 0 ? "=" : "-";
    }

    public void printStatus(final Class<?> testClass) {
        printStatus(testClass, Map.of());
    }

    public void printStatus(final Class<?> testClass, final Map<String, Object> properties) {
        format("%s%n%s%n", getLine().repeat(30), title(testClass, properties));
        format("%d tests passed in %d ms%n", passed, System.currentTimeMillis() - start);
        println("Version: " + getVersion(testClass));
        println("");
    }

    private static String title(final Class<?> testClass, final Map<String, Object> properties) {
        return String.format("%s %s", testClass.getSimpleName(), properties.isEmpty() ? "" : properties);
    }

    private static String getVersion(final Class<?> clazz) {
        try {
            final ClassLoader cl = clazz.getClassLoader();
            final URL url = cl.getResource(clazz.getName().replace('.', '/') + ".class");
            if (url == null) {
                return "(no manifest)";
            } else {
                final String path = url.getPath();
                final String jarExt = ".jar";
                final int index = path.indexOf(jarExt);
                final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                if (index != -1) {
                    final String jarPath = path.substring(0, index + jarExt.length());
                    try (final JarFile jarFile = new JarFile(new File(new URI(jarPath)))) {
                        final JarEntry entry = jarFile.getJarEntry("META-INF/MANIFEST.MF");
                        return sdf.format(new Date(entry.getTime()));
                    }
                } else {
                    return sdf.format(new Date(new File(path).lastModified()));
                }
            }
        } catch (final IOException | URISyntaxException e) {
            return "error: " + e;
        }
    }

    public <T> T call(final String message, final SupplierE<T> supplier) {
        return testV(() -> get(supplier).either(e -> fail(e, "%s", message), Function.identity()));
    }

    public void shouldFail(final String message, @SuppressWarnings("TypeMayBeWeakened") final RunnableE action) {
        test(() -> get(action).either(e -> null, v -> fail("%s", message)));
    }

    public <T> T fail(final String format, final Object... args) {
        return fail(new AssertionError(String.format(format, args)));
    }

    public <T> T fail(final Throwable throwable) {
        return fail(throwable, "%s: %s", throwable.getClass().getSimpleName(), throwable.getMessage());
    }

    public <T> T fail(final Throwable throwable, final String format, final Object... args) {
        final String message = String.format(format, args);
        println("ERROR: " + message);
        throw throwable instanceof Error ? (Error) throwable : new AssertionError(throwable);
    }

    public void checkTrue(final boolean condition, final String message, final Object... args) {
        if (!condition) {
            fail(message, args);
        }
    }

    public static <T> Either<Exception, T> get(final SupplierE<T> supplier) {
        return supplier.get();
    }

    @FunctionalInterface
    public interface SupplierE<T> extends Supplier<Either<Exception, T>> {
        T getE() throws Exception;

        @Override
        default Either<Exception, T> get() {
            try {
                return Either.right(getE());
            } catch (final Exception e) {
                return Either.left(e);
            }
        }
    }

    @FunctionalInterface
    public interface RunnableE extends SupplierE<Void> {
        void run() throws Exception;

        @Override
        default Void getE() throws Exception {
            run();
            return null;
        }
    }
}
