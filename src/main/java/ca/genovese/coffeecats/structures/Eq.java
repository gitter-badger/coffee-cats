package ca.genovese.coffeecats.structures;

import java.util.function.Function;

/**
 * A type class used to determine equality between 2 instances of the same
 * type. Any 2 instances `x` and `y` are equal if `eqv(x, y)` is `true`.
 * Moreover, `eqv` should form an equivalence relation.
 */
public interface Eq<A> {

  /**
   * Returns `true` if `x` and `y` are equivalent, `false` otherwise.
   */
  Boolean eqv(A x, A y);

  /**
   * Returns `false` if `x` and `y` are equivalent, `true` otherwise.
   */
  default Boolean neqv(A x, A y) {
    return !eqv(x, y);
  }

  /**
   * Constructs a new `Eq` instance for type `B` where 2 elements are
   * equivalent iff `eqv(f(x), f(y))`.
   */
  default <B> Eq<B> on(Function<B, A> f) {
    return new Eq<B>() {
      @Override
      public Boolean eqv(B x, B y) {
        return Eq.this.eqv(f.apply(x), f.apply(y));
      }
    };
  }
}
