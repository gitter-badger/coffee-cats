package ca.genovese.coffeecats.laws;

import ca.genovese.coffeecats.cats.Apply;
import ca.genovese.coffeecats.cats.Functor;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public interface ApplyLaws<A, B, C, F> extends FunctorLaws<A, B, C, F> {

  abstract Apply<F> getApply();

  @Override
  default Functor<F> getFunctor() {
    return getApply();
  }

  abstract Kind<F, Function<A, B>> getRandomFAB();

  abstract Kind<F, Function<B, C>> getRandomFBC();

  void testApplyComposition();

  default void applyComposition() {
    for (int i = 0; i < getExecutionCount(); i++) {
      Kind<F, A> fa = getRandomFA();
      Kind<F, Function<A, B>> fab = getRandomFAB();
      Kind<F, Function<B, C>> fbc = getRandomFBC();
      assertEquals("applying over 2 functions should produce the same value as applying their composition",
          getApply().apply(getApply().apply(fa, fab), fbc),
          getApply().apply(fa, getApply().apply(fab, getApply().map(fbc, bc -> ab -> ab.andThen(bc)))));

    }
  }
}
