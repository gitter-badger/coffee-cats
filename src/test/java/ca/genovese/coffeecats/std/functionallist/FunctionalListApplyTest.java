package ca.genovese.coffeecats.std.functionallist;

import ca.genovese.coffeecats.cats.Apply;
import ca.genovese.coffeecats.laws.ApplyLaws;
import ca.genovese.coffeecats.types.FunctionalList;
import ca.genovese.coffeecats.util.Kind;
import org.junit.Test;

import java.util.function.Function;

public class FunctionalListApplyTest extends FunctionalListFunctorTest
    implements ApplyLaws<Integer, Double, String, FunctionalList> {
  @Override
  public Apply<FunctionalList> getApply() {
    return new ListApply();
  }

  @Override
  public Kind<FunctionalList, Function<Integer, Double>> getRandomFAB() {
    int count = rnd.nextInt(10);
    FunctionalList<Function<Integer, Double>> list = new FunctionalList.Nil<>();
    for (int i = 0; i < count; i++) {
      list = new FunctionalList.Cons<>(getRandomAB(), list);
    }
    return list;
  }

  @Override
  public Kind<FunctionalList, Function<Double, String>> getRandomFBC() {
    int count = rnd.nextInt(10);
    FunctionalList<Function<Double, String>> list = new FunctionalList.Nil<>();
    for (int i = 0; i < count; i++) {
      list = new FunctionalList.Cons<>(getRandomBC(), list);
    }
    return list;
  }

  @Override
  @Test
  public void testApplyComposition() {
    applyComposition();
  }
}
