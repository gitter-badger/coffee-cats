package ca.genovese.coffeecats.examples.composits;

import ca.genovese.coffeecats.examples.list.ListApply;
import ca.genovese.coffeecats.examples.list.ListFunctor;
import ca.genovese.coffeecats.examples.option.OptionApply;
import ca.genovese.coffeecats.examples.option.OptionFunctor;
import ca.genovese.coffeecats.structures.Apply;
import ca.genovese.coffeecats.structures.ApplyLaws;
import ca.genovese.coffeecats.structures.Functor;
import ca.genovese.coffeecats.structures.FunctorLaws;
import ca.genovese.coffeecats.types.List;
import ca.genovese.coffeecats.types.Option;
import ca.genovese.coffeecats.util.Kind;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.Random;
import java.util.function.Function;

public class ListOfOptionApplyTest extends ListOfOptionFunctorTest
    implements ApplyLaws<Integer, Double, String, Kind<List, Option>> {

  @Override
  public Apply<Kind<List, Option>> getApply() {
    return new ListApply().compose(new OptionApply());
  }

  @Override
  public Kind<Kind<List, Option>, Function<Integer, Double>> getRandomFAB() {
    int count = rnd.nextInt(10);
    List<Option<Function<Integer, Double>>> list = new List.Nil<>();
    for (int i = 0; i < count; i++) {
      if(rnd.nextBoolean()) {
        list = new List.Cons<>(new Option.Some<>(getRandomAB()), list);
      } else {
        list = new List.Cons<>(new Option.None<>(), list);
      }
    }
    return (Kind<Kind<List, Option>, Function<Integer, Double>>)(Object) list;
  }

  @Override
  public Kind<Kind<List, Option>, Function<Double, String>> getRandomFBC() {
    int count = rnd.nextInt(10);
    List<Option<Function<Double, String>>> list = new List.Nil<>();
    for (int i = 0; i < count; i++) {
      if(rnd.nextBoolean()) {
        list = new List.Cons<>(new Option.Some<>(getRandomBC()), list);
      } else {
        list = new List.Cons<>(new Option.None<>(), list);
      }
    }
    return (Kind<Kind<List, Option>, Function<Double, String>>)(Object) list;
  }

  @Override
  public void testApplyComposition() {
    applyComposition();
  }
}
