package ca.genovese.coffeecats.std.functionallist;

import ca.genovese.coffeecats.cats.Functor;
import ca.genovese.coffeecats.laws.FunctorLaws;
import ca.genovese.coffeecats.types.FunctionalList;
import ca.genovese.coffeecats.util.Kind;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.Random;
import java.util.function.Function;

public class FunctionalListFunctorTest implements FunctorLaws<Integer, Double, String, FunctionalList> {
  Random rnd = new SecureRandom();

  @Override
  public Functor<FunctionalList> getFunctor() {
    return new ListFunctor();
  }

  @Override
  public Kind<FunctionalList, Integer> getRandomFA() {
    int count = rnd.nextInt(10);
    FunctionalList<Integer> list = new FunctionalList.Nil<>();
    for (int i = 0; i < count; i++) {
      list = new FunctionalList.Cons<>(rnd.nextInt(10), list);
    }
    return list;
  }

  @Override
  public Function<Integer, Double> getRandomAB() {
    if (true || rnd.nextBoolean()) {
      return i -> i.doubleValue();
    } else {
      double v = rnd.nextDouble();
      return i -> i * v;

    }
  }

  @Override
  public Function<Double, String> getRandomBC() {
    if (true || rnd.nextBoolean()) {
      return d -> d.toString();
    } else {
      return d -> d.toString().concat(d.toString());
    }
  }

  @Override
  @Test
  public void testCovariantIdentity() {
    covariantIdentity();
  }

  @Override
  @Test
  public void testCovariantComposition() {
    covariantComposition();
  }
}
