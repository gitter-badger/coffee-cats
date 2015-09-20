package ca.genovese.coffeecats.cats;

import ca.genovese.coffeecats.algebra.Monoid;
import ca.genovese.coffeecats.util.Kind;

/**
 * MonoidK is a universal monoid which operates on kinds.
 *
 * This type class is useful when its type parameter F[_] has a
 * structure that can be combined for any particular type, and which
 * also has an "empty" representation. Thus, MonoidK is like a Monoid
 * for kinds (i.e. parameterized types).
 *
 * A MonoidK[F] can produce a Monoid[F[A]] for any type A.
 *
 * Here's how to distinguish Monoid and MonoidK:
 *
 *  - Monoid[A] allows A values to be combined, and also means there
 *    is an "empty" A value that functions as an identity.
 *
 *  - MonoidK[F] allows two F[A] values to be combined, for any A.  It
 *    also means that for any A, there is an "empty" F[A] value. The
 *    combination operation and empty value just depend on the
 *    structure of F, but not on the structure of A.
 */
public interface MonoidK <F extends Kind> extends SemigroupK<F> {
  /**
   * Given a type A, create an "empty" F[A] value.
   */
  <A> Kind<F, A> empty();

  /**
   * Given a type A, create a concrete Monoid[F[A]].
   */
  default <A> Monoid<Kind<F, A>> algebra() {
    return new Monoid<Kind<F, A>>() {
      @Override
      public Kind<F, A> empty() {
        return MonoidK.this.empty();
      }

      @Override
      public Kind<F, A> combine(Kind<F, A> x, Kind<F, A> y) {
        return MonoidK.this.combine(x, y);
      }
    };
  }
}
