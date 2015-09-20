package ca.genovese.coffeecats.cats;

import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * a Monad equipped with an additional method which allows us to
 * create an "Empty" value for the Monad (for whatever "empty" makes
 * sense for that particular monad). This is of particular interest to
 * us since it allows us to add a `filter` method to a Monad, which is
 * used when pattern matching or using guards in for comprehensions.
 */
public interface MonadFilter<F extends Kind> extends Monad<F> {
    <A> Kind<F, A> empty();

  default <A> Kind<F, A>filter(Kind<F, A> fa, Predicate<A> p) {
    return flatMap(fa, a -> p.test(a) ? pure(a) : empty());
  }

  default <A> Kind<F, A>filterM(Kind<F, A> fa, Function<A, Kind<F, Boolean>> p) {
    return flatMap(fa, a -> flatMap(p.apply(a), b -> b ? pure(a) : empty()));
  }
}
