package ca.genovese.coffeecats.structures;

import ca.genovese.coffeecats.util.Kind;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public interface FunctorLaws<A, B, C, F extends Kind> {

  default Integer getExecutionCount() {
    return 500;
  }

  Functor<F> getFunctor();

  Kind<F, A> getRandomF();

  Function<A, B> getRandomAB();

  Function<B, C> getRandomBC();

  /**
   * applying the identity function via map should result in the original F&lt;A&gt;
   */
  @Test
  default void covariantIdentity() {
    for (int i = 0; i < getExecutionCount(); i++) {
      Kind<F, A> fa = getRandomF();
      assertEquals("applying the identity function via map should result in the original value",
          fa,
          getFunctor().map(fa, Function.identity()));
    }
  }

  @Test
  default void covariantComposition() {
    for (int i = 0; i < getExecutionCount(); i++) {
      Kind<F, A> fa = getRandomF();
      Function<A, B> f = getRandomAB();
      Function<B, C> g = getRandomBC();
      assertEquals("mapping over 2 functions should produce the same value as mapping over their composition",
          getFunctor().map(getFunctor().map(fa, f), g),
          getFunctor().map(fa, f.andThen(g)));
    }
  }
}
