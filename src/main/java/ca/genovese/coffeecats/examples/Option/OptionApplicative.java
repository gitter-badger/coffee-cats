package ca.genovese.coffeecats.examples.option;

import ca.genovese.coffeecats.structures.Applicative;
import ca.genovese.coffeecats.types.Option;
import ca.genovese.coffeecats.util.HigherKind;
import java.util.function.Function;

public class OptionApplicative implements Applicative<Option> {
  @Override
  public <A> HigherKind<Option, A> pure(A a) {
    return Option.create(a);
  }

  @Override
  public <A, B> HigherKind<Option, B> apply(HigherKind<Option, A> fa, HigherKind<Option,
      Function<A, B>> ff) {
    if (ff instanceof Option.None || fa instanceof Option.None) {
      return new Option.None<>();
    } else {
      Function<A, B> f = (Function<A, B>) ff.getRealType().get();
      A a = (A) fa.getRealType().get();
      return pure(f.apply(a));
    }
  }
}
