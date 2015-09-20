package ca.genovese.coffeecats.laws;

import ca.genovese.coffeecats.cats.Monad;
import ca.genovese.coffeecats.cats.MonadFilter;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public interface MonadFilterLaws<A, B, C, F extends Kind> extends MonadLaws<A, B, C, F> {

  MonadFilter<F> getMonadFilter();

  @Override
  default Monad<F> getMonad() {
    return getMonadFilter();
  }

  void testMonadFilterLeftEmpty();

  default void monadFilterLeftEmpty() {
    for (int i = 0; i < getExecutionCount(); i++) {
      A a = getRandomA();
      Function<A, Kind<F, B>> f = getRandomAFB();
      //F.pure(a).flatMap(f) <-> f(a)
      assertEquals(
          "",
          getMonadFilter().flatMap(getMonadFilter().empty(), f),
          getMonadFilter().empty());
    }
  }


  void testMonadFilterRightEmpty();

  default void monadFilterRightEmpty() {
    for (int i = 0; i < getExecutionCount(); i++) {
      Kind<F, A> fa = getRandomFA();

      assertEquals(
          "",
          getMonadFilter().flatMap(fa, a -> getMonadFilter().empty()),
          getMonadFilter().empty());
    }
  }
}
