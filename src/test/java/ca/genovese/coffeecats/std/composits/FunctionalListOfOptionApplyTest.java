package ca.genovese.coffeecats.std.composits;

import ca.genovese.coffeecats.std.functionallist.ListApply;
import ca.genovese.coffeecats.std.option.OptionApply;
import ca.genovese.coffeecats.cats.Apply;
import ca.genovese.coffeecats.laws.ApplyLaws;
import ca.genovese.coffeecats.types.FunctionalList;
import ca.genovese.coffeecats.types.Option;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

public class FunctionalListOfOptionApplyTest extends FunctionalListOfOptionFunctorTest
    implements ApplyLaws<Integer, Double, String, Kind<FunctionalList, Option>> {

  @Override
  public Apply<Kind<FunctionalList, Option>> getApply() {
    return new ListApply().compose(new OptionApply());
  }

  @Override
  public Kind<Kind<FunctionalList, Option>, Function<Integer, Double>> getRandomFAB() {
    int count = rnd.nextInt(10);
    FunctionalList<Option<Function<Integer, Double>>> list = new FunctionalList.Nil<>();
    for (int i = 0; i < count; i++) {
      if(rnd.nextBoolean()) {
        list = new FunctionalList.Cons<>(new Option.Some<>(getRandomAB()), list);
      } else {
        list = new FunctionalList.Cons<>(new Option.None<>(), list);
      }
    }
    return (Kind<Kind<FunctionalList, Option>, Function<Integer, Double>>)(Object) list;
  }

  @Override
  public Kind<Kind<FunctionalList, Option>, Function<Double, String>> getRandomFBC() {
    int count = rnd.nextInt(10);
    FunctionalList<Option<Function<Double, String>>> list = new FunctionalList.Nil<>();
    for (int i = 0; i < count; i++) {
      if(rnd.nextBoolean()) {
        list = new FunctionalList.Cons<>(new Option.Some<>(getRandomBC()), list);
      } else {
        list = new FunctionalList.Cons<>(new Option.None<>(), list);
      }
    }
    return (Kind<Kind<FunctionalList, Option>, Function<Double, String>>)(Object) list;
  }

  @Override
  public void testApplyComposition() {
    applyComposition();
  }
}
