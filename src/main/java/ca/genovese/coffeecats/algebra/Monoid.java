package ca.genovese.coffeecats.algebra;

public interface Monoid<A> extends Semigroup<A> {
  /**
   * Return the identity element for this monoid.
   */
  A empty();

  /**
   * Tests if `a` is the identity.
   */
  default Boolean isEmpty(Eq<A> ev, A a) {
    return ev.eqv(a, empty());
  }


  /**
   * Return `a` appended to itself `n` times.
   */
  default A combineN(A a, int n) {
    if (n < 0) {
      throw new IllegalArgumentException("Repeated combining for monoids must have n >= 0");
    } else {
      A result = empty();
      for (int i = n; i > 0; i--) {
        result = combine(result, a);
      }
      return result;
    }
  }

  /**
   * Given a sequence of `as`, sum them using the monoid and return the total.
   */
  default A combineAll(Iterable<A> as) {
    A result = empty();
    for (A a : as) {
      result = combine(result, a);
    }
    return result;
  }
}
