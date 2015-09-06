package ca.genovese.coffeecats.examples.option;

import ca.genovese.coffeecats.structures.Functor;
import java.util.function.Function;

public class OptionFunctor implements Functor<Option> {
  @Override
  public <A, B> Option<B> map(Option fa, Function<A, B> f) {
    if(fa instanceof OptionNone) {
      return new OptionNone<>();
    } else {
      return new OptionSome<>(f.apply((A)fa.get()));
    }
  }
}
