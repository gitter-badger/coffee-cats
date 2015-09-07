package ca.genovese.coffeecats.util.types.function;

import ca.genovese.coffeecats.util.types.tuple.Tuple2;

public interface Function2<A, B, R> {
  /**
   * Applies this function to the given argument.
   *
   * @param a the first function argument
   * @param b the second function argument
   * @return the function result
   */
  R apply(A a, B b);

  default Function1<Tuple2<A, B>, R> tupled() {
    return t -> apply(t.a, t.b);
  }
}
