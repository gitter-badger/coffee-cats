package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.Functor;
import ca.genovese.coffeecats.types.List;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

public class ListFunctor implements Functor<List> {
  @Override
  public <A, B> Kind<List, B> map(Kind<List, A> fa, Function<A, B> f) {
    if (fa instanceof List.Nil) {
      return new List.Nil<>();
    } else {
      return new List.Cons<>(f.apply((A) fa.getRealType().getHead()), (List<B>) map(fa.getRealType().getTail(), f));
    }
  }
}
