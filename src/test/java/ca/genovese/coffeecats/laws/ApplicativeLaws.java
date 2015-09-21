package ca.genovese.coffeecats.laws;

import ca.genovese.coffeecats.cats.Applicative;
import ca.genovese.coffeecats.cats.Apply;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public interface ApplicativeLaws<A, B, C, F> extends ApplyLaws<A, B, C, F> {

  Applicative<F> getApplicative();

  @Override
  default Apply<F> getApply() {
    return getApplicative();
  }

  A getRandomA();

  void testApplicativeIdentity();

  default void applicativeIdentity() {
    for (int i = 0; i < getExecutionCount(); i++) {
      Kind<F, A> fa = getRandomFA();
      assertEquals("applying pure of the identity function should produce the original F<A>",
          getApplicative().apply(fa, getApplicative().pure(Function.identity())),
          fa);
    }
  }

  void testApplicativeHomomorphism();

  default void applicativeHomomorphism() {
    for (int i = 0; i < getExecutionCount(); i++) {
      A a = getRandomA();
      Function<A, B> ab = getRandomAB();

      assertEquals("applying pure of a function to the pure of a value should produce " +
              "the same result as the pure of the function applied to the value",
          getApplicative().apply(getApplicative().pure(a), getApplicative().pure(ab)),
          getApplicative().pure(ab.apply(a)));
    }
  }

  void testApplicativeInterchange();

  default void applicativeInterchange() {
    for (int i = 0; i < getExecutionCount(); i++) {
      A a = getRandomA();
      Kind<F, Function<A, B>> ff = getRandomFAB();

      assertEquals("applying an F of a function to the pure of a value should produce " +
              "the same result as applying pure of a value to the F of a function",
          getApplicative().apply(getApplicative().pure(a), ff),
          getApplicative().apply(ff, getApplicative().pure(f -> f.apply(a))));
    }
  }

  void testApplicativeMap();

  default void applicativeMap() {
    for (int i = 0; i < getExecutionCount(); i++) {
      Kind<F, A> fa = getRandomFA();
      Function<A, B> f = getRandomAB();

      assertEquals("applying pure of a function to  F of a value should produce " +
              "the same result as mapping the function over the F of a value",
          getApplicative().map(fa, f),
          getApplicative().apply(fa, getApplicative().pure(f)));
    }
  }

  /**
   * This law is [[applyComposition]] stated in terms of `pure`. It is a
   * combination of [[applyComposition]] and [[applicativeMap]] and hence not
   * strictly necessary.
   */
  void testApplicativeComposition();

  default void applicativeComposition() {
    for (int i = 0; i < getExecutionCount(); i++) {
      Kind<F, A> fa = getRandomFA();
      Kind<F, Function<A, B>> fab = getRandomFAB();
      Kind<F, Function<B, C>> fbc = getRandomFBC();
      BiFunction<Function<A, B>, Function<B, C>, Function<A, C>> compose = (a, b) -> a.andThen(b);

      Kind<F, C> expected = getApplicative().apply(fa,
          getApplicative().apply(fab,
              getApplicative().apply(fbc, getApplicative().pure(bc -> ab -> compose.apply(ab, bc)))));

      assertEquals("applying pure of a function to the pure of a value should produce " +
              "the same result as the pure of the function applied to the value",
          expected,
          getApplicative().apply(getApplicative().apply(fa, fab), fbc));
    }
  }
}
