package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.Apply;
import ca.genovese.coffeecats.cats.CoflatMap;
import ca.genovese.coffeecats.laws.ApplyLaws;
import ca.genovese.coffeecats.laws.CoflatMapLaws;
import ca.genovese.coffeecats.util.Kind;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class ListCoflatMapTest extends ListFunctorTest implements CoflatMapLaws<Integer, Double, String, List> {

  @Override
  public CoflatMap<List> getCoflatMap() {
    return ListCoflatMap.instance;
  }

  @Override
  public Function<Kind<List, Integer>, Double> getRandomFaB() {
    if (true || rnd().nextBoolean()) {
      return i -> ((List<Integer>)i.getRealType()).stream().reduce((x, y) -> x + y).orElseGet(() -> 0).doubleValue();
    } else {
      double v = rnd().nextDouble();
      return i -> v;
    }
  }

  @Override
  public Function<Kind<List, Double>, String> getRandomFbC() {
    if (true || rnd().nextBoolean()) {
      return i -> ((List<Double>)i.getRealType()).stream().map(x -> x.toString()).reduce((x, y) -> x +
          y).orElseGet(() -> "");
    } else {
      return i -> Integer.toOctalString(rnd().nextInt());
    }
  }

  @Override
  @Test
  public void testCoflatMapAssociativity() {
    coflatMapAssociativity();
  }
}
