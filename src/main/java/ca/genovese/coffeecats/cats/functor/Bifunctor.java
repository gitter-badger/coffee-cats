package ca.genovese.coffeecats.cats.functor;

import ca.genovese.coffeecats.util.Kind2;

import java.util.function.Function;

public interface Bifunctor<F> {
  /**
   * The quintessential method of the Bifunctor trait, it applies a
   * function to each "side" of the bifunctor.
   */
  <A, B, C, D> Kind2<F, C, D> bimap(Kind2<F, A, B> fab, Function<A, C> f, Function<B, D> g);

  // derived methods

  /**
   * apply a function to the "left" functor
   */
  default <A, B, C> Kind2<F, C, B> leftMap(Kind2<F, A, B> fab, Function<A, C> f) {
    return bimap(fab, f, Function.identity());
  }

  /**
   * apply a function ro the "right" functor
   */
  default <A, B, C> Kind2<F, A, C> rightMap(Kind2<F, A, B> fab, Function<B, C> f) {
    return bimap(fab, Function.identity(), f);
  }

}
