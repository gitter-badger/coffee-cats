package ca.genovese.coffeecats.examples.composits;

import ca.genovese.coffeecats.examples.list.ListFunctor;
import ca.genovese.coffeecats.examples.option.OptionFunctor;
import ca.genovese.coffeecats.structures.Functor;
import ca.genovese.coffeecats.structures.FunctorLaws;
import ca.genovese.coffeecats.types.List;
import ca.genovese.coffeecats.types.Option;
import ca.genovese.coffeecats.util.Kind;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.Random;
import java.util.function.Function;

public class ListOfOptionFunctorTest implements FunctorLaws<Integer, Double, String, Kind<List, Option>> {
  Random rnd = new SecureRandom();

  @Override
  public Functor<Kind<List, Option>> getFunctor() {
    return new ListFunctor().compose(new OptionFunctor());
  }

  @Override
  public  Kind<Kind<List, Option>, Integer> getRandomFA() {
    int count = rnd.nextInt(5000);
    List<Option<Integer>> list = new List.Nil<>();
    for (int i = 0; i < count; i++) {
      if (rnd.nextBoolean()) {
        list = new List.Cons<>(new Option.None<>(), list);
      } else {
        list = new List.Cons<>(new Option.Some<>(rnd.nextInt()), list);
      }

    }
    return (Kind<Kind<List, Option>, Integer>) (Object) list;
  }

  @Override
  public  Function<Integer, Double> getRandomAB() {
    if (rnd.nextBoolean()) {
      return i -> i.doubleValue();
    } else {
      double v = rnd.nextDouble();
      return i -> i * v;

    }
  }

  @Override
  public  Function<Double, String> getRandomBC() {
    if (rnd.nextBoolean()) {
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
