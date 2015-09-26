package ca.genovese.coffeecats.cats.arrow;

import ca.genovese.coffeecats.cats.functor.Strong;
import ca.genovese.coffeecats.util.Kind2;
import ca.genovese.coffeecats.util.types.tuple.Tuple2;

import java.util.function.Function;

public interface Arrow<F> extends Split<F>, Strong<F>, Category<F> {

  <A, B> Kind2<F, A, B> lift(Function<A, B> f);

  default <A, B, C, D> Kind2<F, C, D> dimap(Kind2<F, A, B> fab, Function<C, A> f, Function<B, D> g) {
    return compose(lift(g), andThen(lift(f), fab));
  }

  //Should be private
  default <X, Y> Kind2<F, Tuple2<X, Y>, Tuple2<Y, X>>swap () {
    return lift(t -> new Tuple2<>(t.b, t.a));
  }

  default <A, B, C> Kind2<F, Tuple2<C, A>, Tuple2<C, B>> second(Kind2<F, A, B> fab) {
    Kind2<F, Tuple2<A, C>, Tuple2<B, C>> first = first(fab);
    return compose(swap(), compose(first, swap()));
  }

}
