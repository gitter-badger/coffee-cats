package ca.genovese.coffeecats.std.functionallist;

import ca.genovese.coffeecats.cats.Apply;
import ca.genovese.coffeecats.types.FunctionalList;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

public class ListApply extends ListFunctor implements Apply<FunctionalList> {
  @Override
  public <A, B> Kind<FunctionalList, B> apply(Kind<FunctionalList, A> fa, Kind<FunctionalList, Function<A, B>> ff) {
    Function<A, B> f;
    FunctionalList<B> work = new FunctionalList.Nil<B>();

    while (ff.getRealType() instanceof FunctionalList.Cons) {
      f = (Function<A, B>) ff.getRealType().getHead();
      FunctionalList<A> as = fa.getRealType();
      while (as instanceof FunctionalList.Cons) {
        A a = as.getHead();
        B b = f.apply(a);
        work = new FunctionalList.Cons<>(b, work);
        as = as.getTail();
      }
      ff = ff.getRealType().getTail();
    }

    FunctionalList<B> result = new FunctionalList.Nil<>();
    for (B b : work) {
      result = new FunctionalList.Cons<>(b, result);
    }

    return result;
  }
}
