package ca.genovese.coffeecats.examples.option;

import ca.genovese.coffeecats.structures.Apply;
import ca.genovese.coffeecats.types.Option;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

public class OptionApply extends OptionFunctor implements Apply<Option> {
  @Override
  public <A, B> Kind<Option, B> apply(Kind<Option, A> fa, Kind<Option, Function<A, B>> ff) {
    if (ff instanceof Option.None || fa instanceof Option.None) {
      return new Option.None<>();
    } else {
      Function<A, B> f = (Function<A, B>) ff.getRealType().get();
      A a = (A) fa.getRealType().get();
      return new Option.Some<>(f.apply(a));
    }
  }
}
