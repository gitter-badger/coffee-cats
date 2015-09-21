package ca.genovese.coffeecats.laws;

import ca.genovese.coffeecats.cats.CoflatMap;
import ca.genovese.coffeecats.cats.Functor;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public interface CoflatMapLaws<A, B, C, F> extends FunctorLaws<A, B, C, F> {

  CoflatMap<F> getCoflatMap();

  @Override
  default Functor<F> getFunctor() {
    return getCoflatMap();
  }

  Function<Kind<F, A>, B> getRandomFaB();

  Function<Kind<F, B>, C> getRandomFbC();

  void testCoflatMapAssociativity();

  default void coflatMapAssociativity() {
    for (int i = 0; i < getExecutionCount(); i++) {
      Kind<F, A> fa = getRandomFA();
      Function<Kind<F, A>, B> f = getRandomFaB();
      Function<Kind<F, B>, C> g = getRandomFbC();
      assertEquals("",
          getCoflatMap().coflatMap(getCoflatMap().coflatMap(fa, f), g),
          getCoflatMap().coflatMap(fa, x -> g.apply(getCoflatMap().coflatMap(x, f))));
    }
  }
}
