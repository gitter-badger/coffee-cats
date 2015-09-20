package ca.genovese.coffeecats.std.composits;

import ca.genovese.coffeecats.std.functionallist.ListFunctor;
import ca.genovese.coffeecats.std.option.OptionFunctor;
import ca.genovese.coffeecats.cats.Functor;
import ca.genovese.coffeecats.laws.FunctorLaws;
import ca.genovese.coffeecats.types.FunctionalList;
import ca.genovese.coffeecats.types.Option;
import ca.genovese.coffeecats.util.Kind;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.Random;
import java.util.function.Function;

public class FunctionalListOfOptionFunctorTest implements FunctorLaws<Integer, Double, String, Kind<FunctionalList, Option>> {
  Random rnd = new SecureRandom();

  @Override
  public Functor<Kind<FunctionalList, Option>> getFunctor() {
    return new ListFunctor().compose(new OptionFunctor());
  }

  @Override
  public  Kind<Kind<FunctionalList, Option>, Integer> getRandomFA() {
    int count = rnd.nextInt(5000);
    FunctionalList<Option<Integer>> list = new FunctionalList.Nil<>();
    for (int i = 0; i < count; i++) {
      if (rnd.nextBoolean()) {
        list = new FunctionalList.Cons<>(new Option.None<>(), list);
      } else {
        list = new FunctionalList.Cons<>(new Option.Some<>(rnd.nextInt()), list);
      }

    }
    return (Kind<Kind<FunctionalList, Option>, Integer>) (Object) list;
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