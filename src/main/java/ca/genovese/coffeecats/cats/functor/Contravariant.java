package ca.genovese.coffeecats.cats.functor;

import ca.genovese.coffeecats.cats.Functor;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

public interface Contravariant<F> extends Invariant<F> {
  <A, B> Kind<F, B> contramap(Kind<F, A> fa, Function<B, A> f);

  @Override
  default <A, B> Kind<F, B> imap(Kind<F, A> fa, Function<A, B> f, Function<B, A> g) {
    return contramap(fa, g);
  }

  default <G> Functor<Kind<F, G>> compose(Contravariant<G> g) {
    return new Composite<F, G>(this, g);
  }

  default <G> Contravariant<Kind<F, G>> composeWithFunctor(Functor<G> g) {
    return new CovariantComposite<F, G>(this, g);
  }

  class Composite<F, G> implements Functor<Kind<F, G>> {
    private final Contravariant<F> F;
    private final Contravariant<G> G;

    public Composite(Contravariant<F> f, Contravariant<G> g) {
      F = f;
      G = g;
    }

    @Override
    public <A, B> Kind<Kind<F, G>, B> map(Kind<Kind<F, G>, A> fga, Function<A, B> f) {
      Kind<F, Kind<G, B>> result = F.contramap((Kind<F, Kind<G, A>>) fga, gb -> G.contramap(gb, f));
      return (Kind<Kind<F, G>, B>) result;
    }
  }

  class CovariantComposite<F, G> implements Contravariant<Kind<F, G>> {
    private final Contravariant<F> F;
    private final Functor<G> G;

    public CovariantComposite(Contravariant<F> f, Functor<G> g) {
      F = f;
      G = g;
    }

    @Override
    public <A, B> Kind<Kind<F, G>, B> contramap(Kind<Kind<F, G>, A> fga, Function<B, A> f) {
      return (Kind<Kind<F, G>, B>) F.contramap((Kind<F, Kind<G, A>>) fga, (Kind<G, B> gb) -> G.map(gb, f));
    }
  }
}
