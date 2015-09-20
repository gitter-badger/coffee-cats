package ca.genovese.coffeecats.laws;

import ca.genovese.coffeecats.cats.MonoidK;
import ca.genovese.coffeecats.cats.SemigroupK;
import ca.genovese.coffeecats.util.Kind;

import static org.junit.Assert.assertEquals;

public interface MonoidKLaws<A, F extends Kind> extends SemigroupKLaws<A, F> {

  MonoidK<F> getMonoidK();

  @Override
  default SemigroupK<F> getSemigroupK() {
    return getMonoidK();
  }

  void testMonoidKLeftIdentity();

  default void monoidKLeftIdentity() {
    execLaw(() -> {
      Kind<F, A> a = getRandomFA();
      assertEquals("the semigroup combine should be associative",
          getMonoidK().combine(getMonoidK().empty(), a),
          a);
    });
  }

  void testMonoidKRightIdentity();

  default void monoidKRightIdentity() {
    execLaw(() -> {
      Kind<F, A> a = getRandomFA();
      assertEquals("the semigroup combine should be associative",
          getMonoidK().combine(a, getMonoidK().empty()),
          a);
    });
  }
}
