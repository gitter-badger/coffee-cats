package ca.genovese.coffeecats.structures;

import ca.genovese.coffeecats.util.Kind;
import java.util.function.Function;

public interface Monad<F extends Kind> extends Applicative<F> {
  @Override
  <A> Kind<F, A> pure(A a);

  <A, B> Kind<F, B> flatMap(Kind<F, A> fa, Function<A, Kind<F, B>> f);

  @Override
  default <A, B> Kind<F, B> apply(Kind<F, A> fa, Kind<F, Function<A, B>> ff) {
    return flatMap(ff, f -> map(fa, f));
  }

  @Override
  default <A, B> Kind<F, B> map(Kind<F, A> fa, Function<A, B> f) {
    return flatMap(fa, a -> pure(f.apply(a)));
  }

  default <A> Kind<F, A> flatten(Kind<F, Kind<F, A>> ffa) {
    return flatMap(ffa, fa -> fa);
  }

}
