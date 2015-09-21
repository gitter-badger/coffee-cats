package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.Monad;
import ca.genovese.coffeecats.util.Kind;

import java.util.List;
import java.util.function.Function;

public interface ListMonad extends ListApplicative, ListFlatMap, Monad<List> {
  ListMonad instance = new ListMonad() {
  };

  @Override
  default <A, B> Kind<List, B> map(Kind<List, A> fa, Function<A, B> f) {
    return ListApplicative.super.map(fa, f);
  }

  @Override
  default <A, B> Kind<List, B> apply(Kind<List, A> fa, Kind<List, Function<A, B>> ff) {
    return ListApplicative.super.apply(fa, ff);
  }
}
