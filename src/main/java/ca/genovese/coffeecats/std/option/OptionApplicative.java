package ca.genovese.coffeecats.std.option;

import ca.genovese.coffeecats.cats.Applicative;
import ca.genovese.coffeecats.types.Option;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

public class OptionApplicative implements Applicative<Option> {
  @Override
  public <A> Kind<Option, A> pure(A a) {
    return Option.create(a);
  }

  @Override
  public <A, B> Kind<Option, B> apply(Kind<Option, A> fa, Kind<Option, Function<A, B>> ff) {
    if (ff instanceof Option.None || fa instanceof Option.None) {
      return new Option.None<>();
    } else {
      Function<A, B> f = (Function<A, B>) ff.getRealType().get();
      A a = (A) fa.getRealType().get();
      return pure(f.apply(a));
    }
  }
}
