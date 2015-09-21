package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.Apply;
import ca.genovese.coffeecats.util.Kind;
import ca.genovese.coffeecats.util.ListKind;

import java.util.List;
import java.util.function.Function;

public interface ListApply extends ListFunctor, Apply<List> {
  ListApply instance = new ListApply() {
  };

  @Override
  default <A, B> Kind<List, B> apply(Kind<List, A> fa, Kind<List, Function<A, B>> ff) {
    List<Function<A, B>> functions = ff.getRealType();
    List<B> result = ListInstance.getNewList(functions);

    for (Function<A, B> f : functions) {
      result.addAll(map(fa, f).getRealType());
    }

    return new ListKind<>(result);
  }
}
