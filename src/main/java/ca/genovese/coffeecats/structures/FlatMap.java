package ca.genovese.coffeecats.structures;

import ca.genovese.coffeecats.util.Kind;
import ca.genovese.coffeecats.util.types.tuple.Tuple2;

import java.util.function.Function;
import java.util.function.Supplier;

public interface FlatMap<F extends Kind> extends Apply<F> {
  <A, B> Kind<F, B> flatMap(Kind<F, A> fa, Function<A, Kind<F, B>> f);

  /**
   * also commonly called join
   */
  default <A> Kind<F, A> flatten(Kind<F, Kind<F, A>> ffa) {
    return flatMap(ffa, fa -> fa);
  }

  @Override
  default <A, B> Kind<F, B> apply(Kind<F, A> fa, Kind<F, Function<A, B>> ff) {
    return flatMap(ff, f -> map(fa, f));
  }

  /**
   * Pair `A` with the result of function application.
   */
  default <A, B> Kind<F, Tuple2<A, B>> mproduct(Kind<F, A> fa, Function<A, Kind<F, B>> f) {
    return flatMap(fa, a -> map(f.apply(a), b -> new Tuple2<>(a, b)));
  }

  /**
   * `if` lifted into monad.
   */
  default <B> Kind<F, B> ifM(Kind<F, Boolean> fa, Supplier<Kind<F, B>> ifTrue, Supplier<Kind<F, B>> ifFalse) {
    return flatMap(fa, a -> {
      if (a) { return ifTrue.get(); } else { return ifFalse.get(); }
    });
  }
}
