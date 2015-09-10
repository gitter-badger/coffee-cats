package ca.genovese.coffeecats.examples.option;

import ca.genovese.coffeecats.structures.Apply;
import ca.genovese.coffeecats.structures.ApplyLaws;
import ca.genovese.coffeecats.types.Option;
import ca.genovese.coffeecats.util.Kind;
import org.junit.Test;

import java.util.function.Function;

public class OptionApplyTest extends OptionFunctorTest implements ApplyLaws<Integer, Double, String, Option> {
  @Override
  public Apply<Option> getApply() {
    return new OptionApply();
  }

  @Override
  public Kind<Option, Function<Integer, Double>> getRandomFAB() {
    if (rnd.nextBoolean()) {
      return new Option.None<>();
    } else {
      if (rnd.nextBoolean()) {
        return new Option.Some<>(i -> i.doubleValue());
      } else {
        double v = rnd.nextDouble();
        return new Option.Some<>(i -> i * v);
      }
    }

  }

  @Override
  public Kind<Option, Function<Double, String>> getRandomFBC() {
    if (rnd.nextBoolean()) {
      return new Option.None<>();
    } else {
      if (rnd.nextBoolean()) {
        return new Option.Some<>(d -> d.toString());
      } else {
        return new Option.Some<>(d -> d.toString().concat(d.toString()));
      }
    }
  }

  @Override
  @Test
  public void testApplyComposition() {
    applyComposition();
  }
}
