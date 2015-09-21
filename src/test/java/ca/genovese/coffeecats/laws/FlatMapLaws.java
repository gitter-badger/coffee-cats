package ca.genovese.coffeecats.laws;

import ca.genovese.coffeecats.cats.Applicative;
import ca.genovese.coffeecats.cats.Apply;
import ca.genovese.coffeecats.cats.FlatMap;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public interface FlatMapLaws<A, B, C, F> extends ApplyLaws<A, B, C, F> {

  FlatMap<F> getFlatMap();

  @Override
  default Apply<F> getApply() {
    return getFlatMap();
  }

  Function<A, Kind<F, B>> getRandomAFB();
  Function<B, Kind<F, C>> getRandomBFC();

  void testFlatMapAssociativity();

  default void flatMapAssociativity() {
    for (int i = 0; i < getExecutionCount(); i++) {
      Kind<F, A> fa = getRandomFA();
      Function<A, Kind<F, B>> f = getRandomAFB();
      Function<B, Kind<F, C>> g = getRandomBFC();

      assertEquals("applying pure of the identity function should produce the original F<A>",
          getFlatMap().flatMap(getFlatMap().flatMap(fa, f), g),
          getFlatMap().flatMap(fa, a -> getFlatMap().flatMap(f.apply(a), g)));
    }
  }

  void testFlatMapConsistentApply();

  default void flatMapConsistentApply() {
    for (int i = 0; i < getExecutionCount(); i++) {
      Kind<F, A> fa = getRandomFA();
      Kind<F, Function<A, B>> fab = getRandomFAB();

      assertEquals("applying pure of a function to the pure of a value should produce " +
              "the same result as the pure of the function applied to the value",
          getFlatMap().apply(fa, fab),
          getFlatMap().flatMap(fab, f -> getFlatMap().map(fa, f)));
    }
  }
}
