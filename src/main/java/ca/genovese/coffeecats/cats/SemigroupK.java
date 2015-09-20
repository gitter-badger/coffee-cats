package ca.genovese.coffeecats.cats;

import ca.genovese.coffeecats.algebra.Semigroup;
import ca.genovese.coffeecats.util.Kind;

/**
 * SemigroupK is a universal semigroup which operates on kinds.
 *
 * This type class is useful when its type parameter F[_] has a
 * structure that can be combined for any particular type. Thus,
 * SemigroupK is like a Semigroup for kinds (i.e. parameterized
 * types).
 *
 * A SemigroupK[F] can produce a Semigroup[F[A]] for any type A.
 *
 * Here's how to distinguish Semigroup and SemigroupK:
 *
 *  - Semigroup[A] allows two A values to be combined.
 *
 *  - SemigroupK[F] allows two F[A] values to be combined, for any A.
 *    The combination operation just depends on the structure of F,
 *    but not the structure of A.
 */
public interface SemigroupK<F> {
  /**
   * Combine two F[A] values.
   */
  <A> Kind<F, A>combine(Kind<F, A> x,Kind<F, A> y);

  /**
   * Given a type A, create a concrete Semigroup[F[A]].
   */
  default <A> Semigroup<Kind<F, A>> algebra() {
    return (x, y) -> SemigroupK.this.combine(x, y);
  }
}
