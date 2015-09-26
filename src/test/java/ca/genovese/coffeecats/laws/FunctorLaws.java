package ca.genovese.coffeecats.laws;

import ca.genovese.coffeecats.cats.Functor;
import ca.genovese.coffeecats.cats.functor.Invariant;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public interface FunctorLaws<A, B, C, F> extends InvariantLaws<A, B, C, F> {

  Functor<F> getFunctor();

  @Override
  default Invariant<F> getInvariant() {
    return getFunctor();
  }

  void testCovariantIdentity();
  /**
   * applying the identity function via map should result in the original F&lt;A&gt;
   */
  default void covariantIdentity() {
    execLaw(() -> {
      Kind<F, A> fa = getRandomFA();
      assertEquals("applying the identity function via map should result in the original value",
          fa,
          getFunctor().map(fa, Function.identity()));
    });
  }

  void testCovariantComposition();

  default void covariantComposition() {
    execLaw(() -> {
      Kind<F, A> fa = getRandomFA();
      Function<A, B> f = getRandomAB();
      Function<B, C> g = getRandomBC();
      assertEquals("mapping over 2 functions should produce the same value as mapping over their composition",
          getFunctor().map(getFunctor().map(fa, f), g),
          getFunctor().map(fa, f.andThen(g)));
    });
  }
}
