package ca.genovese.coffeecats.structures;

import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public interface ComonadLaws<A, B, C, F extends Kind> extends CoflatMapLaws<A, B, C, F> {

  Comonad<F> getComonad();

  @Override
  default CoflatMap<F> getCoflatMap() {
    return getComonad();
  }

  void testComonadLeftIdentity();

  default void comonadLeftIdentity() {
    for (int i = 0; i < getExecutionCount(); i++) {
      Kind<F, A> fa = getRandomFA();
      assertEquals("",
          getComonad().coflatMap(fa, x -> getComonad().extract(x)),
          fa);
    }
  }

  void testComonadRightIdentity();

  default void comonadRightIdentity() {
    for (int i = 0; i < getExecutionCount(); i++) {
      Kind<F, A> fa = getRandomFA();
      Function<Kind<F, A>, B> f = getRandomFaB();
      assertEquals("",
          getComonad().extract(getComonad().coflatMap(fa, f)),
          f.apply(fa));
    }
  }
}
