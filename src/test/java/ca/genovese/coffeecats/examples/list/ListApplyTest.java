package ca.genovese.coffeecats.examples.list;

import ca.genovese.coffeecats.structures.Apply;
import ca.genovese.coffeecats.structures.ApplyLaws;
import ca.genovese.coffeecats.types.List;
import ca.genovese.coffeecats.util.Kind;
import org.junit.Test;

import java.util.function.Function;

public class ListApplyTest extends ListFunctorTest implements ApplyLaws<Integer, Double, String, List> {
  @Override
  public Apply<List> getApply() {
    return new ListApply();
  }

  @Override
  public Kind<List, Function<Integer, Double>> getRandomFAB() {
    int count = rnd.nextInt(10);
    List<Function<Integer, Double>> list = new List.Nil<>();
    for (int i = 0; i < count; i++) {
      list = new List.Cons<>(getRandomAB(), list);
    }
    return list;
  }

  @Override
  public Kind<List, Function<Double, String>> getRandomFBC() {
    int count = rnd.nextInt(10);
    List<Function<Double, String>> list = new List.Nil<>();
    for (int i = 0; i < count; i++) {
      list = new List.Cons<>(getRandomBC(), list);
    }
    return list;
  }

  @Override
  @Test
  public void testApplyComposition() {
    applyComposition();
  }
}
