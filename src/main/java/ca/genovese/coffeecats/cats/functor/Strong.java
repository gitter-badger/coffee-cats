package ca.genovese.coffeecats.cats.functor;

import ca.genovese.coffeecats.util.Kind2;
import ca.genovese.coffeecats.util.types.tuple.Tuple2;

public interface Strong<F> extends Profunctor<F> {
  <A, B, C> Kind2<F, Tuple2<A, C>, Tuple2<B, C>> first(Kind2<F, A, B> fab);

  <A, B, C> Kind2<F, Tuple2<C, A>, Tuple2<C, B>> second(Kind2<F, A, B> fab);
}
