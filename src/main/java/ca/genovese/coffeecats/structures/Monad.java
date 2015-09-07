package ca.genovese.coffeecats.structures;

import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

public interface Monad<F extends Kind> extends Applicative<F>, FlatMap<F> {
  @Override
  <A> Kind<F, A> pure(A a);

  @Override
  default <A, B> Kind<F, B> map(Kind<F, A> fa, Function<A, B> f) {
    return flatMap(fa, a -> pure(f.apply(a)));
  }
}
