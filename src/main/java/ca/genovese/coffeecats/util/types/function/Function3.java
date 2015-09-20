package ca.genovese.coffeecats.util.types.function;

import ca.genovese.coffeecats.util.types.tuple.Tuple3;

import java.util.function.Function;

public interface Function3<A, B, C, R> {
  /**
   * Applies this function to the given argument.
   *
   * @param a the first function argument
   * @param b the second function argument
   * @return the function result
   */
  R apply(A a, B b, C c);

  default Function<Tuple3<A, B, C>, R> tupled() {
    return t -> apply(t.a, t.b, t.c);
  }
}
