package ca.genovese.coffeecats.std.composits;

import ca.genovese.coffeecats.cats.Apply;
import ca.genovese.coffeecats.laws.ApplyLaws;
import ca.genovese.coffeecats.std.list.ListApply;
import ca.genovese.coffeecats.std.option.OptionApply;
import ca.genovese.coffeecats.types.Option;
import ca.genovese.coffeecats.util.Kind;

import java.util.List;
import java.util.function.Function;

public class ListOfOptionApplyTest extends ListOfOptionFunctorTest
    implements ApplyLaws<Integer, Double, String, Kind<List, Option>> {

  @Override
  public Apply<Kind<List, Option>> getApply() {
    return ListApply.instance.compose(new OptionApply());
  }

  @Override
  public Kind<Kind<List, Option>, Function<Integer, Double>> getRandomFAB() {
    Kind<List, Kind<Option, Function<Integer, Double>>> list = makeList(() -> {
      if (rnd.nextBoolean()) {
        return new Option.None<>();
      } else {
        return new Option.Some<>(getRandomAB());
      }
    });


    return (Kind<Kind<List, Option>, Function<Integer, Double>>) (Object) list;
  }

  @Override
  public Kind<Kind<List, Option>, Function<Double, String>> getRandomFBC() {
    Kind<List, Kind<Option, Function<Double, String>>> list = makeList(() -> {
      if (rnd.nextBoolean()) {
        return new Option.None<>();
      } else {
        return new Option.Some<>(getRandomBC());
      }
    });

    return (Kind<Kind<List, Option>, Function<Double, String>>) (Object) list;
  }

  @Override
  public void testApplyComposition() {
    applyComposition();
  }
}
