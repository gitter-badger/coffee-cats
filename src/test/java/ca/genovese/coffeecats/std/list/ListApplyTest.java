package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.Apply;
import ca.genovese.coffeecats.laws.ApplyLaws;
import ca.genovese.coffeecats.util.Kind;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

public class ListApplyTest extends ListFunctorTest implements ApplyLaws<Integer, Double, String, List> {
  @Override
  public Apply<List> getApply() {
    return ListApply.instance;
  }

  @Override
  public Kind<List, Function<Integer, Double>> getRandomFAB() {
    return makeList(this::getRandomAB);
  }

  @Override
  public Kind<List, Function<Double, String>> getRandomFBC() {
    return makeList(this::getRandomBC);
  }

  @Override
  @Test
  public void testApplyComposition() {
    applyComposition();
  }
}
