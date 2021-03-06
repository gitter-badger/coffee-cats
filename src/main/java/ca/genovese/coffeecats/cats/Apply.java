package ca.genovese.coffeecats.cats;

import ca.genovese.coffeecats.util.Kind;
import ca.genovese.coffeecats.util.types.function.Function3;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Weaker version of Apply[F]; has apply but not pure.
 * <p/>
 * Must obey the laws defined in [[laws.ApplyLaws]].
 */
public interface Apply<F> extends Functor<F> {

  /**
   * Given a value and a function in the Apply context, applies the
   * function to the value.
   */
  <A, B> Kind<F, B> apply(Kind<F, A> fa, Kind<F, Function<A, B>> f);

  /**
   * apply2 is a binary version of apply, defined in terms of apply.
   */
  default <A, B, Z> Kind<F, Z> apply2(Kind<F, A> fa, Kind<F, B> fb, Kind<F, BiFunction<A, B, Z>> ff) {
    return apply(fa, apply(fb, map(ff, f -> b -> a -> f.apply(a, b))));
  }


  /**
   * Applies the pure (binary) function f to the effectful values fa and fb.
   * <p/>
   * map2 can be seen as a binary version of [[cats.Functor]]#map.
   */
  default <A, B, Z> Kind<F, Z> map2(Kind<F, A> fa, Kind<F, B> fb, BiFunction<A, B, Z> f) {
    return apply(fa, map(fb, b -> a -> f.apply(a, b)));
  }

  /**
   * Applies the pure (binary) function f to the effectful values fa, fb, and fc.
   */
  default <A, B, C, Z> Kind<F, Z> map3(Kind<F, A> fa, Kind<F, B> fb, Kind<F, C> fc, Function3<A, B, C, Z> f) {
    return apply(fa, map2(fb, fc, (b, c) -> a -> f.apply(a, b, c)));
  }

  default <A, B> Function<Kind<F, A>, Kind<F, B>> flip(Kind<F, Function<A, B>> f) {
    return fa -> apply(fa, f);
  }

  default <G> Apply<Kind<F, G>> compose(Apply<G> g) {
    return new CompositeApply<>(this, g);
  }

  class CompositeApply<F, G> extends CompositeFunctor<F, G>
      implements Apply<Kind<F, G>> {
    private final Apply<G> fg;
    private final Apply<F> ff;

    public CompositeApply(Apply<F> ff, Apply<G> fg) {
      super(ff, fg);
      this.fg = fg;
      this.ff = ff;
    }

    @Override
    public <A, B> Kind<Kind<F, G>, B> apply(Kind<Kind<F, G>, A> fg_a, Kind<Kind<F, G>, Function<A, B>> fg_ab) {
      return (Kind<Kind<F, G>, B>) ff.<Kind<G, A>, Kind<G, B>>apply((Kind<F, Kind<G, A>>) fg_a,
          ff.map((Kind<F, Kind<G, Function<A, B>>>) (Object) fg_ab, gab -> ga -> fg.apply(ga, gab)));
    }
  }

}
