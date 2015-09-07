package ca.genovese.coffeecats.examples.option;

import ca.genovese.coffeecats.structures.Apply;
import ca.genovese.coffeecats.structures.ApplyLaws;
import ca.genovese.coffeecats.types.Option;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

public class OptionApplyTest extends OptionFunctorTest implements ApplyLaws<Integer, Double, String, Option> {
  @Override
  public Apply<Option> getApply() {
    return new OptionApply();
  }

  @Override
  public Kind<Option, Function<Integer, Double>> getRandomFAB() {
    return null;
  }

  @Override
  public Kind<Option, Function<Double, String>> getRandomFBC() {
    return null;
  }
}
