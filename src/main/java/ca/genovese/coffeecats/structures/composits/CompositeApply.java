package ca.genovese.coffeecats.structures.composits;

import ca.genovese.coffeecats.structures.Apply;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

public class CompositeApply<F extends Kind, G extends Kind> extends CompositeFunctor<F, G>
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
    Kind<F, Kind<G, A>> f_ga = (Kind<F, Kind<G, A>>) fg_a;
    Kind<F, Kind<G, Function<A, B>>> f_gab = (Kind<F, Kind<G, Function<A, B>>>) (Object) fg_ab;
    Kind<F, Kind<G, B>> b = ff.apply(f_ga, ff.map(f_gab, gab -> ga -> fg.apply(ga, gab)));
    return (Kind<Kind<F, G>, B>) (Object) b;
  }
}
