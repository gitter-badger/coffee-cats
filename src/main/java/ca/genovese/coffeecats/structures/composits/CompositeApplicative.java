package ca.genovese.coffeecats.structures.composits;

import ca.genovese.coffeecats.structures.Applicative;
import ca.genovese.coffeecats.util.Kind;

public class CompositeApplicative<F extends Kind, G extends Kind> extends CompositeApply<F, G>
    implements Applicative<Kind<F, G>> {
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
