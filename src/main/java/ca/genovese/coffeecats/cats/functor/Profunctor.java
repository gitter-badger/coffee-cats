package ca.genovese.coffeecats.cats.functor;

import ca.genovese.coffeecats.util.Kind2;

import java.util.function.Function;

/**
 * A [[Profunctor]] is a [[Contravariant]] functor on its first type parameter
 * and a [[Functor]] on its second type parameter.
 *
 * Must obey the laws defined in [[laws.ProfunctorLaws]].
 */
public interface Profunctor<F> {
  /**
   * contramap on the first type parameter and map on the second type parameter
   */
  <A, B, C, D> Kind2<F, C, D> dimap(Kind2<F, A, B> fab, Function<C, A> f, Function<B, D> g);


  /**
   * contramap on the first type parameter
   */
  default <A, B, C> Kind2<F, C, B> lmap(Kind2<F, A, B> fab, Function<C, A> f) {
    return dimap(fab, f, Function.identity());
  }

  /**
   * map on the second type parameter
   */
  default <A, B, C> Kind2<F, A, C> rmap(Kind2<F, A, B> fab, Function<B, C> f) {
    return dimap(fab, Function.identity(), f);
  }
}
