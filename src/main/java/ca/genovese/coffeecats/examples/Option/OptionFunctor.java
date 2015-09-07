package ca.genovese.coffeecats.examples.option;

import ca.genovese.coffeecats.structures.Functor;
import ca.genovese.coffeecats.types.Option;
import ca.genovese.coffeecats.util.HigherKind;
import java.util.function.Function;

public class OptionFunctor implements Functor<Option> {
  @Override
  public <A, B> HigherKind<Option, B> map(HigherKind<Option, A> fa, Function<A, B> f) {
    if (fa instanceof Option.None) {
      return new Option.None<>();
    } else {
      return new Option.OptionSome<>(f.apply((A) fa.getRealType().get()));
    }
  }
}
