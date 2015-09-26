package ca.genovese.coffeecats.cats.arrow;

import ca.genovese.coffeecats.util.Kind2;
import ca.genovese.coffeecats.util.types.tuple.Tuple2;

public interface Split<F> extends Compose<F> {
  <A, B, C, D> Kind2<F, Tuple2<A, C>, Tuple2<B, D>> split(Kind2<F, A, B> f, Kind2<F, C, D> g);
}
