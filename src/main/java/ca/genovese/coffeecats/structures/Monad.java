package ca.genovese.coffeecats.structures;

import ca.genovese.coffeecats.util.HigherKind;
import java.util.function.Function;

public interface Monad<F extends HigherKind> extends Applicative<F> {
  @Override
  <A> HigherKind<F, A> pure(A a);

  <A, B> HigherKind<F, B> flatMap(HigherKind<F, A> fa, Function<A, HigherKind<F, B>> f);

  @Override
  default <A, B> HigherKind<F, B> apply(HigherKind<F, A> fa, HigherKind<F, Function<A, B>> ff) {
    return flatMap(ff, f -> map(fa, f));
  }

  @Override
  default <A, B> HigherKind<F, B> map(HigherKind<F, A> fa, Function<A, B> f) {
    return flatMap(fa, a -> pure(f.apply(a)));
  }

  default <A> HigherKind<F, A> flatten(HigherKind<F, HigherKind<F, A>> ffa) {
    return flatMap(ffa, fa -> fa);
  }

}
