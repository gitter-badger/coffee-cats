package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.Apply;
import ca.genovese.coffeecats.types.List;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

public class ListApply extends ListFunctor implements Apply<List> {
  @Override
  public <A, B> Kind<List, B> apply(Kind<List, A> fa, Kind<List, Function<A, B>> ff) {
    Function<A, B> f;
    List<B> work = new List.Nil<B>();

    while (ff.getRealType() instanceof List.Cons) {
      f = (Function<A, B>) ff.getRealType().getHead();
      List<A> as = fa.getRealType();
      while (as instanceof List.Cons) {
        A a = as.getHead();
        B b = f.apply(a);
        work = new List.Cons<>(b, work);
        as = as.getTail();
      }
      ff = ff.getRealType().getTail();
    }

    List<B> result = new List.Nil<>();
    for (B b : work) {
      result = new List.Cons<>(b, result);
    }

    return result;
  }
}
