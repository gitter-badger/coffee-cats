package ca.genovese.coffeecats.cats.arrow;

import ca.genovese.coffeecats.algebra.Monoid;
import ca.genovese.coffeecats.cats.MonoidK;
import ca.genovese.coffeecats.util.Kind;
import ca.genovese.coffeecats.util.Kind2;

public interface Category<F> extends Compose<F> {

  <A> Kind2<F, A, A> id();

  // TODO - there must be a better way to work around java's type system here
  @Override
  default <A> MonoidK<F> algebraK() {
    return new MonoidK<F>() {
      @Override
      public <A> Kind<F, A> empty() {
        return (Kind<F, A>) Category.this.id();
      }

      @Override
      public <A> Kind<F, A> combine(Kind<F, A> x, Kind<F, A> y) {
        return (Kind<F, A>) Category.this.compose((Kind2<F, A, A>) x, (Kind2<F, A, A>) y);
      }
    };
  }

  default <A> Monoid<Kind2<F, A, A>> algebra() {
    return new Monoid<Kind2<F, A, A>>() {
      @Override
      public Kind2<F, A, A> empty() {
        return Category.this.id();
      }

      @Override
      public Kind2<F, A, A> combine(Kind2<F, A, A> x, Kind2<F, A, A> y) {
        return Category.this.compose(x, y);
      }
    };
  }
}
