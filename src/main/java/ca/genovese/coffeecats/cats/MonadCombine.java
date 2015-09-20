package ca.genovese.coffeecats.cats;

import ca.genovese.coffeecats.util.Kind;

/**
 * The combination of a Monad with a MonoidK
 */
public interface MonadCombine<F> extends MonadFilter<F>, Alternative<F> {
  /**
   * fold over the inner structure to combining all the values with
   * our combine method inherited from MonoidK. The result is for us
   * to accumulate all of the "interesting" values of the innner G, so
   * if G is Option, we collect all the Some values, if G is Xor,
   * we collect all the Right values, etc.
   */
  default <G, A> Kind<F, A> unite(Kind<F, Kind<G, A>> fga, Foldable<G> g) {
    return flatMap(fga, ga -> g.foldLeft(ga, empty(), (acc, a) -> combine(acc, pure(a))));
  }
}
