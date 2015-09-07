package ca.genovese.coffeecats.structures.composits;

import ca.genovese.coffeecats.structures.Functor;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

public class CompositeFunctor<F extends Kind, G extends Kind> implements Functor<Kind<F, G>> {
  private final Functor<G> fg;
  private final Functor<F> ff;

  public CompositeFunctor(Functor<F> ff, Functor<G> fg) {
    this.fg = fg;
    this.ff = ff;
  }

  @Override
  public <A, B> Kind<Kind<F, G>, B> map(Kind<Kind<F, G>, A> fg_a, Function<A, B> f) {
    Kind<F, Kind<G, A>> f_ga = (Kind<F, Kind<G, A>>) fg_a;
    Kind<F, Kind<G, B>> mapResult_f_gb = cmap(f_ga, f);
    Object mapResult_o = mapResult_f_gb;
    Kind<Kind<F, G>, B> mapResult_fg_b = (Kind<Kind<F, G>, B>) mapResult_o;
    return mapResult_fg_b;
  }

  public <A, B> Kind<F, Kind<G, B>> cmap(Kind<F, Kind<G, A>> fga, Function<A, B> f) {
    return ff.map(fga, ga -> fg.map(((G) ga), f));
  }
}
