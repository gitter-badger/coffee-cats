package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.Apply;
import ca.genovese.coffeecats.cats.FlatMap;
import ca.genovese.coffeecats.laws.ApplyLaws;
import ca.genovese.coffeecats.laws.FlatMapLaws;
import ca.genovese.coffeecats.util.Kind;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

public class ListFlatMapTest extends ListApplyTest implements FlatMapLaws<Integer, Double, String, List> {
  @Override
  public FlatMap<List> getFlatMap() {
    return ListFlatMap.instance;
  }

  @Override
  public Function<Integer, Kind<List, Double>> getRandomAFB() {
    Kind<List, Double> list = makeList(rnd()::nextDouble);
    return i -> list;
  }

  @Override
  public Function<Double, Kind<List, String>> getRandomBFC() {
    Kind<List, String> list = makeList(() -> Integer.toHexString(rnd().nextInt()));
    return d -> list;
  }

  @Override
  @Test
  public void testFlatMapAssociativity() {
    flatMapAssociativity();
  }

  @Override
  @Test
  public void testFlatMapConsistentApply() {
    flatMapConsistentApply();
  }
}
