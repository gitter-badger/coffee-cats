package ca.genovese.coffeecats.cats;

import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

/**
 * Monad.
 *
 * Allows composition of dependent effectful functions.
 *
 * See: [[http://homepages.inf.ed.ac.uk/wadler/papers/marktoberdorf/baastad.pdf Monads for functional programming]]
 *
 * Must obey the laws defined in [[laws.MonadLaws]].
 */
public interface Monad<F> extends Applicative<F>, FlatMap<F> {
  @Override
  default <A, B> Kind<F, B> map(Kind<F, A> fa, Function<A, B> f) {
    return flatMap(fa, a -> pure(f.apply(a)));
  }
}
