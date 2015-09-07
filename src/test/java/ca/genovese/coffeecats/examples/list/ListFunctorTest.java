package ca.genovese.coffeecats.examples.list;

import ca.genovese.coffeecats.examples.list.ListFunctor;
import ca.genovese.coffeecats.structures.Functor;
import ca.genovese.coffeecats.structures.FunctorLaws;
import ca.genovese.coffeecats.types.List;
import ca.genovese.coffeecats.util.Kind;

import java.security.SecureRandom;
import java.util.Random;
import java.util.function.Function;

public class ListFunctorTest extends FunctorLaws<Integer, Double, String, List> {
  Random rnd = new SecureRandom();

  @Override
  protected Functor<List> getFunctor() {
    return new ListFunctor();
  }

  @Override
  protected Kind<List, Integer> getRandomF() {
    int count = rnd.nextInt(5000);
    List<Integer> list = new List.Nil<>();
    for(int i = 0; i < count; i++) {
      list = new List.Cons<>(rnd.nextInt(), list);
    }
    return list;
  }

  @Override
  protected Function<Integer, Double> getRandomAB() {
    if(rnd.nextBoolean()) {
      return i -> i.doubleValue();
    } else {
      double v = rnd.nextDouble();
      return i -> i * v;

    }
  }

  @Override
  protected Function<Double, String> getRandomBC() {
    if(rnd.nextBoolean()) {
      return d -> d.toString();
    } else {
      return d -> d.toString().concat(d.toString());
    }
  }
}
