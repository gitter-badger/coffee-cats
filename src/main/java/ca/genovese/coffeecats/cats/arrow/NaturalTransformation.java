package ca.genovese.coffeecats.cats.arrow;

import ca.genovese.coffeecats.util.Kind;

public interface NaturalTransformation<F, G> {
  <A> Kind<G, A> apply(Kind<F, A> fa);
}
