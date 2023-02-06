package phoupraw.mcmod.createsdelight.api;

import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Predicate;
public final class Lambdas {
    public static <T> Predicate<@Nullable T> always() {
        return t -> true;
    }
    public static <T> Predicate<@Nullable T> never() {
        return t -> false;
    }
    public static <T> Consumer<T> nothing() {
        return t -> {};
    }

    private Lambdas(){}
}
