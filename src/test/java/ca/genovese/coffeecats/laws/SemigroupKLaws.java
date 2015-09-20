package ca.genovese.coffeecats.laws;

import ca.genovese.coffeecats.cats.SemigroupK;
import ca.genovese.coffeecats.util.Kind;

import static org.junit.Assert.assertEquals;

public interface SemigroupKLaws<A, F extends Kind> extends BaseLaw {

  SemigroupK<F> getSemigroupK();

  Kind<F, A> getRandomFA();

  void testSemigroupKAssociative();

  default void semigroupKAssociative() {
    execLaw(() -> {
      Kind<F, A> a = getRandomFA();
      Kind<F, A> b = getRandomFA();
      Kind<F, A> c = getRandomFA();
      assertEquals("the semigroup combine should be associative",
          getSemigroupK().combine(a, getSemigroupK().combine(b, c)),
          getSemigroupK().combine(getSemigroupK().combine(a, b), c));
    });
  }
}
