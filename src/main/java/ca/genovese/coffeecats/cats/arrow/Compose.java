package ca.genovese.coffeecats.cats.arrow;

import ca.genovese.coffeecats.algebra.Semigroup;
import ca.genovese.coffeecats.cats.SemigroupK;
import ca.genovese.coffeecats.util.Kind;
import ca.genovese.coffeecats.util.Kind2;

public interface Compose<F> {

  <A, B, C> Kind2<F, A, C> compose(Kind2<F, B, C> f, Kind2<F, A, B> g);

  default <A, B, C> Kind2<F, A, C> andThen(Kind2<F, A, B> f, Kind2<F, B, C> g) {
    return compose(g, f);
  }

  // TODO - there must be a better way to work around java's type system here
  default <A> SemigroupK<F> algebraK() {
    return new SemigroupK<F>() {
      @Override
      public <A> Kind<F, A> combine(Kind<F, A> x, Kind<F, A> y) {
        return (Kind<F, A>) Compose.this.compose((Kind2<F, A, A>) x, (Kind2<F, A, A>) y);
      }
    };
  }

  default <A> Semigroup<Kind2<F, A, A>> algebra() {
    return new Semigroup<Kind2<F, A, A>>() {
      @Override
      public Kind2<F, A, A> combine(Kind2<F, A, A> x, Kind2<F, A, A> y) {
        return Compose.this.compose(x, y);
      }
    };
  }
}
