package ca.genovese.coffeecats.cats.functor;

import ca.genovese.coffeecats.cats.Functor;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

/**
 * Must obey the laws defined in [[laws.InvariantLaws]].
 */
public interface Invariant<F> {
  <A, B> Kind<F, B> imap(Kind<F, A> fa, Function<A, B> f, Function<B, A> g);

  /**
   * Compose 2 invariant Functors F and G to get a new Invariant Functor for F[G[_]].
   */
  default <G> Invariant<Kind<F, G>> compose(Invariant<G> g) {
    return new Composite<>(this, g);
  }

  /**
   * Compose the Invariant Functor F with a normal (Covariant) Functor to get a new Invariant Functor for [F[G[_]].
   */
  default <G> Invariant<Kind<F, G>> composeWithFunctor(Functor<G> g) {
    return new CovariantComposite<>(this, g);
  }


  /**
   * Compose the Invariant Functor F with a Contravariant Functor to get a new Invariant Functor for [F[G[_]]].
   */
  default <G> Invariant<Kind<F, G>> composeWithContravariant(Contravariant<G> g) {
    return new ContravariantComposite<>(this, g);
  }

  class Composite<F, G> implements Invariant<Kind<F, G>> {
    private final Invariant<F> F;
    private final Invariant<G> G;

    public Composite(Invariant<F> f, Invariant<G> g) {
      F = f;
      G = g;
    }


    @Override
    public <A, B> Kind<Kind<F, G>, B> imap(Kind<Kind<F, G>, A> fga, Function<A, B> f, Function<B, A> g) {
      return (Kind<Kind<F, G>, B>) F.imap((Kind<F, Kind<G, A>>) fga,
          ga -> G.imap((Kind<G, A>) ga, f, g),
          gb -> G.imap((Kind<G, B>) gb, g, f));
    }
  }

  class CovariantComposite<F, G> implements Invariant<Kind<F, G>> {
    private final Invariant<F> F;
    private final Functor<G> G;

    public CovariantComposite(Invariant<F> f, Functor<G> g) {
      F = f;
      G = g;
    }


    @Override
    public <A, B> Kind<Kind<F, G>, B> imap(Kind<Kind<F, G>, A> fga, Function<A, B> f, Function<B, A> g) {
      return (Kind<Kind<F, G>, B>) F.imap((Kind<F, Kind<G, A>>) fga,
          ga -> G.map((Kind<G, A>) ga, f),
          gb -> G.map((Kind<G, B>) gb, g));
    }
  }

  class ContravariantComposite<F, G> implements Invariant<Kind<F, G>> {
    private final Invariant<F> F;
    private final Contravariant<G> G;

    public ContravariantComposite(Invariant<F> f, Contravariant<G> g) {
      F = f;
      G = g;
    }


    @Override
    public <A, B> Kind<Kind<F, G>, B> imap(Kind<Kind<F, G>, A> fga, Function<A, B> f, Function<B, A> g) {
      return (Kind<Kind<F, G>, B>) F.imap((Kind<F, Kind<G, A>>) fga,
          ga -> G.contramap((Kind<G, A>) ga, g),
          gb -> G.contramap((Kind<G, B>) gb, f));
    }
  }
}
