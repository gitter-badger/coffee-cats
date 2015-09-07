package ca.genovese.coffeecats.structures;

import ca.genovese.coffeecats.structures.composits.CompositeFunctor;
import ca.genovese.coffeecats.util.Kind;
import ca.genovese.coffeecats.util.Unit;
import ca.genovese.coffeecats.util.types.tuple.Tuple2;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Functor.
 *
 * The name is short for "covariant functor".
 *
 * Must obey the laws defined in [[laws.FunctorLaws]].
 */
public interface Functor<F extends Kind> {

  <A, B> Kind<F, B> map(Kind<F, A> fa, Function<A, B> f);

  /**
   * Lift a function f to operate on Functors
   */
  default <A, B> Function<Kind<F, A>, Kind<F, B>> lift(Function<A, B> f) {
    return (Kind<F, A> fa) -> map(fa, f);
  }

  /**
   * Tuple the values in fa with the result of applying a function
   * with the value
   */
  default <A, B> Kind<F, Tuple2<A, B>> zipWith(Kind<F, A> fa, Function<A, B> f) {
    return map(fa, a -> new Tuple2<>(a, f.apply(a)));
  }

  /**
   * Replaces the `A` value in `F[A]` with the supplied value.
   */
  default <B> Kind<F, B> as(Kind<F, ?> fa, Supplier<B> b) {
    return map(fa, a -> b.get());
  }

  /**
   * Empty the fa of the values, preserving the structure
   */
  default Kind<F, Unit> voidMe(Kind<F, ?> fa) {
    return as(fa, () -> Unit.instance);
  }

  /**
   * Compose this functor F with a functor G to produce a composite
   * Functor on G[F[_]], with a map method which uses an A => B to
   * map a G[F[A]] to a G[F[B]].
   */
  default <G extends Kind> Functor<Kind<F, G>> compose(Functor<G> fg) {
    return new CompositeFunctor<>(this, fg);
  }

}

