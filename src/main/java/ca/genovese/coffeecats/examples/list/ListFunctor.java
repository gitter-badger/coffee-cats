package ca.genovese.coffeecats.examples.list;

import ca.genovese.coffeecats.structures.Functor;
import ca.genovese.coffeecats.types.List;
import ca.genovese.coffeecats.util.HigherKind;
import java.util.function.Function;

public class ListFunctor implements Functor<List> {
  @Override
  public <A, B> HigherKind<List, B> map(HigherKind<List, A> fa, Function<A, B> f) {
    if (fa instanceof List.Nil) {
      return new List.Nil<>();
    } else {
      return new List.Cons<>(f.apply((A) fa.getRealType().getHead()),
          (List<B>) map(fa.getRealType().getTail(), f));
    }
  }
}
