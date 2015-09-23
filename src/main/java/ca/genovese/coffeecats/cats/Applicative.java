package ca.genovese.coffeecats.cats;

import ca.genovese.coffeecats.util.Kind;
import ca.genovese.coffeecats.util.types.function.Function3;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Applicative functor.
 * <p/>
 * Allows application of a function in an Applicative context to a value in an Applicative context
 * <p/>
 * See: [[https://www.cs.ox.ac.uk/jeremy.gibbons/publications/iterator.pdf The Essence of the Iterator Pattern]]
 * Also: [[http://staff.city.ac.uk/~ross/papers/Applicative.pdf Applicative programming with effects]]
 * <p/>
 * Must obey the laws defined in [[laws.ApplicativeLaws]].
 */
public interface Applicative<F> extends Apply<F> {
  /**
   * `pure` lifts any value into the Applicative Functor
   * <p/>
   * Applicative[Option].pure(10) = Some(10)
   */
  <A> Kind<F, A> pure(A a);

  @Override
  default <A, B> Kind<F, B> map(Kind<F, A> fa, Function<A, B> f) {
    return apply(fa, pure(f));
  }

  /**
   * Two sequentially dependent Applicatives can be composed.
   * <p/>
   * The composition of Applicatives `F` and `G`, `F[G[x]]`, is also an Applicative
   * <p/>
   * Applicative[Option].compose[List].pure(10) = Some(List(10))
   */
  default <G> Applicative<Kind<F, G>> compose(Applicative<G> g) {
    return new CompositeApplicative<>(this, g);
  }

  default <A, G, B> Kind<Kind<F, G>, B> traverse(Kind<G, A> value,
                                                              Function<A, Kind<F, B>> f,
                                                              Traverse<G> g) {
    return g.traverse(value, f, this);
  }

  default <A, G> Kind<Kind<F, G>, A> sequence(Kind<G, Kind<F, A>> as, Traverse<G> g) {
    return traverse(as, Function.identity(), g);
  }

  class CompositeApplicative<F, G> extends CompositeApply<F, G> implements Applicative<Kind<F, G>> {
    private final Applicative<G> fg;
    private final Applicative<F> ff;

    public CompositeApplicative(Applicative<F> ff, Applicative<G> fg) {
      super(ff, fg);
      this.fg = fg;
      this.ff = ff;
    }

    @Override
    public <A> Kind<Kind<F, G>, A> pure(A a) {
      return (Kind<Kind<F, G>, A>) (Object) ff.pure(fg.pure(a));
    }

  }

}
