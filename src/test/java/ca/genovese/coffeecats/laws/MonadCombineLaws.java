package ca.genovese.coffeecats.laws;

import ca.genovese.coffeecats.cats.MonadCombine;
import ca.genovese.coffeecats.cats.MonadFilter;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public interface MonadCombineLaws<A, B, C, F extends Kind> extends MonadFilterLaws<A, B, C, F> {

  MonadCombine<F> getMonadCombine();

  @Override
  default MonadFilter<F> getMonadFilter() {
    return getMonadCombine();
  }

  void testMonadCombineLeftDistributivity();
//  def monadCombineLeftDistributivity[A, B](fa: F[A], fa2: F[A], f: A => F[B]): IsEq[F[B]] =
//      F.combine(fa, fa2).flatMap(f) <-> F.combine(fa flatMap f, fa2 flatMap f)

  default void monadCombineLeftDistributivity() {
    for (int i = 0; i < getExecutionCount(); i++) {
      Kind<F, A> fa = getRandomFA();
      Kind<F, A> fa2 = getRandomFA();
      Function<A, Kind<F, B>> f = getRandomAFB();
      assertEquals("",
          getMonadCombine().flatMap(getMonadCombine().combine(fa, fa2), f),
          getMonadCombine().combine(getMonadCombine().flatMap(fa, f), getMonadCombine().flatMap(fa2, f)));
    }
  }
}
