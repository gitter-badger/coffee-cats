package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.Fold;
import ca.genovese.coffeecats.cats.Foldable;
import ca.genovese.coffeecats.util.Kind;
import ca.genovese.coffeecats.util.ListKind;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface ListFoldable extends Foldable<List> {
  ListFoldable instance = new ListFoldable() {
  };


  default <A, B> B foldLeft(List<A> fa, B b, BiFunction<B, A, B> f) {
   return foldLeft(new ListKind<>(fa), b, f);
  }

  @Override
  default <A, B> B foldLeft(Kind<List, A> fa, B b, BiFunction<B, A, B> f) {
    List<A> list = fa.getRealType();
    B result = b;
    for (A a : list) {
      result = f.apply(result, a);
    }
    return result;
  }

  @Override
  default <A, B> Fold<B> partialFold(Kind<List, A> fa, Function<A, Fold<B>> f) {
    return Fold.partialIterate((List<A>) fa.getRealType(), f);
  }
}
