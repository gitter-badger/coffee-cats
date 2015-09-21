package ca.genovese.coffeecats.laws;

import ca.genovese.coffeecats.cats.Applicative;
import ca.genovese.coffeecats.cats.Apply;
import ca.genovese.coffeecats.cats.Monad;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public interface MonadLaws<A, B, C, F> extends ApplicativeLaws<A, B, C, F>, FlatMapLaws<A, B, C, F> {

  Monad<F> getMonad();

  @Override
  default Applicative<F> getApplicative() {
    return getMonad();
  }

  @Override
  default Apply<F> getApply() {
    return getMonad();
  }

  void testMonadLeftIdentity();

  default void monadLeftIdentity() {
    for (int i = 0; i < getExecutionCount(); i++) {
      A a = getRandomA();
      Function<A, Kind<F, B>> f = getRandomAFB();
      //F.pure(a).flatMap(f) <-> f(a)
      assertEquals(
          "flatmapping a function over the pure of a value " + "should be equal to applying the function to the value",
          getMonad().flatMap(getMonad().pure(a), f),
          f.apply(a));
    }
  }

  void testMonadRightIdentity();

  default void monadRightIdentity() {
    for (int i = 0; i < getExecutionCount(); i++) {
      Kind<F, A> fa = getRandomFA();

      assertEquals(
          "flatmapping a function over the pure of a value " + "should be equal to applying the function to the value",
          getMonad().flatMap(fa, a -> getMonad().pure(a)),
          fa);
    }
  }
}
