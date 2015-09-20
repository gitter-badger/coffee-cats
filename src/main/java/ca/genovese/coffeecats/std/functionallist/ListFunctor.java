package ca.genovese.coffeecats.std.functionallist;

import ca.genovese.coffeecats.cats.Functor;
import ca.genovese.coffeecats.types.FunctionalList;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

public class ListFunctor implements Functor<FunctionalList> {
  @Override
  public <A, B> Kind<FunctionalList, B> map(Kind<FunctionalList, A> fa, Function<A, B> f) {
    if (fa instanceof FunctionalList.Nil) {
      return new FunctionalList.Nil<>();
    } else {
      return new FunctionalList.Cons<>(f.apply((A) fa.getRealType().getHead()), (FunctionalList<B>) map(fa.getRealType().getTail(), f));
    }
  }
}
