package ca.genovese.coffeecats.laws;

import ca.genovese.coffeecats.cats.Alternative;
import ca.genovese.coffeecats.cats.Applicative;
import ca.genovese.coffeecats.cats.MonoidK;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public interface AlternativeLaws<A, B, C, F> extends ApplicativeLaws<A, B, C, F>, MonoidKLaws<A, F> {

  Alternative<F> getAlternative();

  @Override
  default Applicative<F> getApplicative() {
    return getAlternative();
  }

  @Override
  default MonoidK<F> getMonoidK() {
    return getAlternative();
  }

  void testAlternativeRightAbsorption();

  default void alternativeRightAbsorption() {
    execLaw(() -> {
      Kind<F, Function<A, B>> ff = getRandomFAB();
      assertEquals("Applying an F of a function from A to B to an empty F[A] " + "should produce an empty F[B]",
          getAlternative().apply(getAlternative().empty(), ff),
          getAlternative().<B>empty());
    });
  }

  void testAlternativeLeftDistributivity();

  default void alternativeLeftDistributivity() {
    execLaw(() -> {
      Kind<F, A> fa = getRandomFA();
      Kind<F, A> fa2 = getRandomFA();
      Function<A, B> f = getRandomAB();

      assertEquals("the semigroup combine should be associative",
          getAlternative().map(getAlternative().combine(fa, fa2), f),
          getAlternative().combine(getAlternative().map(fa, f), getAlternative().map(fa2, f)));
    });
  }

  void testAlternativeRightDistributivity();

  default void alternativeRightDistributivity() {
    execLaw(() -> {
      Kind<F, A> fa = getRandomFA();
      Kind<F, Function<A, B>> ff = getRandomFAB();
      Kind<F, Function<A, B>> fg = getRandomFAB();
      assertEquals("the semigroup combine should be associative",
          getAlternative().apply(fa, getAlternative().combine(ff, fg)),
          getAlternative().combine(getAlternative().apply(fa, ff), getAlternative().apply(fa, fg)));
    });
  }
}
