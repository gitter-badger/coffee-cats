package ca.genovese.coffeecats.util;

import java.util.Objects;
import java.util.function.Function;

/**
 * Represents a function that accepts three arguments and produces a result.
 * This is the two-arity specialization of {@link java.util.function.Function}.
 * <p>
 * <p>This is a functional interface</a>
 * whose functional method is {@link #apply(Object, Object, Object)}.
 *
 * @param <T>
 *     the type of the first argument to the function
 * @param <U>
 *     the type of the second argument to the function
 * @param <R>
 *     the type of the result of the function
 * @see Function
 * @since 1.8
 */
@FunctionalInterface
public interface TriFunction<T, U, V, R> {
  /**
   * Applies this function to the given arguments.
   *
   * @param t
   *     the first function argument
   * @param u
   *     the second function argument
   * @return the function result
   */
  R apply(T t, U u, V v);

  /**
   * Returns a composed function that first applies this function to
   * its input, and then applies the {@code after} function to the result.
   * If evaluation of either function throws an exception, it is relayed to
   * the caller of the composed function.
   *
   * @param <W>
   *     the type of output of the {@code after} function, and of the
   *     composed function
   * @param after
   *     the function to apply after this function is applied
   * @return a composed function that first applies this function and then
   * applies the {@code after} function
   * @throws NullPointerException
   *     if after is null
   */
  default <W> TriFunction<T, U, V, W> andThen(Function<? super R, ? extends W> after) {
    Objects.requireNonNull(after);
    return (T t, U u, V v) -> after.apply(apply(t, u, v));
  }
}

