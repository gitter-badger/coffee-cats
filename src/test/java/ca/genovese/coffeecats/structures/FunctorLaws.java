package ca.genovese.coffeecats.structures;

import ca.genovese.coffeecats.structures.Functor;
import ca.genovese.coffeecats.util.Kind;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public abstract class FunctorLaws<A, B, C, F extends Kind> {

  protected Integer getExecutionCount() {
    return 500;
  }

  protected abstract Functor<F> getFunctor();

  protected abstract Kind<F, A> getRandomF();

  protected abstract Function<A, B> getRandomAB();

  protected abstract Function<B, C> getRandomBC();

  /**
   * applying the identity function via map should result in the original F&lt;A&gt;
   */
  @Test
  public void covariantIdentity() {
    for (int i = 0; i < getExecutionCount(); i++) {
      Kind<F, A> fa = getRandomF();
      assertEquals("applying the identity function via map should result in the original value",
          fa,
          getFunctor().map(fa, Function.identity()));
    }
  }

  @Test
  public void covariantComposition() {
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
