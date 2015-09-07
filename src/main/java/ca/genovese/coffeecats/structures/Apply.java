package ca.genovese.coffeecats.structures;

import ca.genovese.coffeecats.structures.composits.CompositeApply;
import ca.genovese.coffeecats.util.Kind;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Weaker version of Apply[F]; has apply but not pure.
 *
 * Must obey the laws defined in [[laws.ApplyLaws]].
 */
public interface Apply<F extends Kind> extends Functor<F> {

  /**
   * Given a value and a function in the Apply context, applies the
   * function to the value.
   */
  <A, B> Kind<F, B> apply(Kind<F, A> fa, Kind<F, Function<A, B>> f);

  /**
   * apply2 is a binary version of apply, defined in terms of apply.
   */
  default <A, B, Z> Kind<F, Z> apply2(Kind<F, A> fa, Kind<F, B> fb,
                                      Kind<F, BiFunction<A, B, Z>> ff) {
    return apply(fa, apply(fb, map(ff, f -> b -> a -> f.apply(a, b))));
  }

  /**
   * Applies the pure (binary) function f to the effectful values fa and fb.
   *
   * map2 can be seen as a binary version of [[cats.Functor]]#map.
   */
  default <A, B, Z> Kind<F, Z> map2(Kind<F, A> fa, Kind<F, B> fb, BiFunction<A,
      B, Z> f) {
    return apply(fa, map(fb, b -> a -> f.apply(a, b)));
  }

  default <A, B> Function<Kind<F, A>, Kind<F, B>> flip(Kind<F, Function<A, B>> f) {
    return fa -> apply(fa, f);
  }

  default <G extends Kind<G, ?>> Apply<Kind<F, G>> compose(Apply<G> g) {
    return new CompositeApply<>(this, g);
  }
}
