package ca.genovese.coffeecats.cats;

import ca.genovese.coffeecats.util.Kind;
import ca.genovese.coffeecats.util.Unit;
import ca.genovese.coffeecats.util.types.tuple.Tuple2;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Functor.
 * <p/>
 * The name is short for "covariant functor".
 */
public interface Functor<F> {

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
  default <G> Functor<Kind<F, G>> compose(Functor<G> fg) {
    return new CompositeFunctor<>(this, fg);
  }

  class CompositeFunctor<F, G> implements Functor<Kind<F, G>> {
    private final Functor<G> fg;
    private final Functor<F> ff;

    public CompositeFunctor(Functor<F> ff, Functor<G> fg) {
      this.fg = fg;
      this.ff = ff;
    }

    @Override
    public <A, B> Kind<Kind<F, G>, B> map(Kind<Kind<F, G>, A> fg_a, Function<A, B> f) {
      return (Kind<Kind<F, G>, B>) ff.map((Kind<F, Kind<G, A>>) fg_a, ga -> fg.map(((Kind<G, A>) ga), f));
    }
  }
}

