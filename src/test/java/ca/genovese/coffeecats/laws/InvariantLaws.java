package ca.genovese.coffeecats.laws;

import ca.genovese.coffeecats.cats.Functor;
import ca.genovese.coffeecats.cats.functor.Invariant;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public interface InvariantLaws<A, B, C, F> extends BaseLaw {

  Invariant<F> getInvariant();

  Kind<F, A> getRandomFA();
  Function<A, B> getRandomAB();
  Function<B, C> getRandomBC();
  Function<B, A> getRandomBA();
  Function<C, B> getRandomCB();


  void testInvariantIdentity();

  default void invariantIdentity() {
    execLaw(() -> {
      Kind<F, A> fa = getRandomFA();
      assertEquals("", getInvariant().imap(fa, Function.identity(), Function.identity()), fa);
    });
  }

  void testInvariantComposition();

  default void invariantComposition() {
    execLaw(() -> {
      Kind<F, A> fa = getRandomFA();
      Function<A, B> f1 = getRandomAB();
      Function<B, C> g1 = getRandomBC();
      Function<B, A> f2 = getRandomBA();
      Function<C, B> g2 = getRandomCB();
      assertEquals("",
          getInvariant().imap(getInvariant().imap(fa, f1, f2), g1, g2),
          getInvariant().imap(fa, f1.andThen(g1), g2.andThen(f2)));
    });
  }
}
