package ca.genovese.coffeecats.cats;

import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

/**
 * Must obey the laws defined in [[laws.CoflatMapLaws]].
 */
public interface CoflatMap<F> extends Functor<F> {
  <A, B> Kind<F, B> coflatMap(Kind<F, A> fa, Function<Kind<F, A>, B> f);

  default <A> Kind<F, Kind<F, A>> coflatten(Kind<F, A> fa) {
    return coflatMap(fa, Function.identity());
  }
}
