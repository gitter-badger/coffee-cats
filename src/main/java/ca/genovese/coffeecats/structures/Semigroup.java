package ca.genovese.coffeecats.structures;

import ca.genovese.coffeecats.types.Option;

/**
 * A semigroup is any set `A` with an associative operation (`combine`).
 */
public interface Semigroup<A> {
  /**
   * Associative operation taking which combines two values.
   */
  A combine(A a, A a2);

  /**
   * Return `a` combined with itself `n` times.
   */
  default A combineN(A a, int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("Repeated combining for semigroups must have n > 0");
    } else {
      A result = null;
      A b = a;
      A extra = a;
      if (n == 1) {
        return a;
      } else {
        for (int k = n; k >= 1; k = k >>> 1) {
          if (k == 1) { result = combine(b, extra); } else {
            A x;
            if ((k & 1) == 1) { x = combine(b, a); } else { x = a; }
            b = combine(b, b);
            extra = x;
          }
        }
      }
      return result;
    }
  }

  /**
   * Given a sequence of `as`, combine them and return the total.
   *
   * If the sequence is empty, returns None. Otherwise, returns Some(total).
   */
  default Option<A> combineAllOption(Iterable<A> as) {
    Option<A> result = new Option.None<>();
    for (A a: as) {
      if(result instanceof Option.None) {
        result = Option.create(a);
      } else {
        result = Option.create(combine(result.get(), a));
      }
    }
    return result;
  }
}
