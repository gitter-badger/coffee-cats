package ca.genovese.coffeecats.examples.option;

import ca.genovese.coffeecats.structures.Applicative;
import ca.genovese.coffeecats.structures.Functor;
import java.util.function.Function;

public class OptionApplicative implements Applicative<Option> {
  @Override
  public <A> Option pure(A a) {
    return Option.create(a);
  }

  @Override
  public <A,B> Option<B> apply(Option fa, Option fab) {
    if(fab instanceof OptionNone || fa instanceof OptionNone) {
      return new OptionNone<>();
    } else {
      Function<A,B> f = (Function<A,B>)fab.get();
      A a = (A)fa.get();
      return pure(f.apply(a));
    }
  }
}
