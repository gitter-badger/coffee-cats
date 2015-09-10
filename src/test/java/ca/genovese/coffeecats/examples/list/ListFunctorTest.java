package ca.genovese.coffeecats.examples.list;

import ca.genovese.coffeecats.structures.Functor;
import ca.genovese.coffeecats.structures.FunctorLaws;
import ca.genovese.coffeecats.types.List;
import ca.genovese.coffeecats.util.Kind;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.Random;
import java.util.function.Function;

public class ListFunctorTest implements FunctorLaws<Integer, Double, String, List> {
  Random rnd = new SecureRandom();

  @Override
  public Functor<List> getFunctor() {
    return new ListFunctor();
  }

  @Override
  public Kind<List, Integer> getRandomF() {
    int count = rnd.nextInt(10);
    List<Integer> list = new List.Nil<>();
    for (int i = 0; i < count; i++) {
      list = new List.Cons<>(rnd.nextInt(10), list);
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
