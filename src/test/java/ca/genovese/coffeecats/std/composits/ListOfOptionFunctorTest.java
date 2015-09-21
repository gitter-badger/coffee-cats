package ca.genovese.coffeecats.std.composits;

import ca.genovese.coffeecats.cats.Functor;
import ca.genovese.coffeecats.laws.FunctorLaws;
import ca.genovese.coffeecats.std.list.BaseListTest;
import ca.genovese.coffeecats.std.list.ListFunctor;
import ca.genovese.coffeecats.std.option.OptionFunctor;
import ca.genovese.coffeecats.types.Option;
import ca.genovese.coffeecats.util.Kind;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class ListOfOptionFunctorTest extends BaseListTest
    implements FunctorLaws<Integer, Double, String, Kind<List, Option>> {
  Random rnd = new SecureRandom();

  @Override
  public Functor<Kind<List, Option>> getFunctor() {
    return ListFunctor.instance.compose(new OptionFunctor());
  }

  @Override
  public Kind<Kind<List, Option>, Integer> getRandomFA() {
    Kind<List, Kind<Option, Integer>> list = makeList(() -> {
      if (rnd.nextBoolean()) {
        return new Option.None<Integer>();
      } else {
        return new Option.Some<Integer>(getRandomA());
      }
    });
    return (Kind<Kind<List, Option>, Integer>) (Object) list;
  }

  @Override
  public Function<Integer, Double> getRandomAB() {
    if (rnd.nextBoolean()) {
      return i -> i.doubleValue();
    } else {
      double v = rnd.nextDouble();
      return i -> i * v;
    }
  }

  @Override
  public Function<Double, String> getRandomBC() {
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
