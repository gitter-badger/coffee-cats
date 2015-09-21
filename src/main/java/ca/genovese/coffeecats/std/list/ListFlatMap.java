package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.FlatMap;
import ca.genovese.coffeecats.util.Kind;
import ca.genovese.coffeecats.util.ListKind;

import java.util.List;
import java.util.function.Function;

public interface ListFlatMap extends ListFunctor, FlatMap<List> {
  ListFlatMap instance = new ListFlatMap() {
  };

  @Override
  default <A, B> Kind<List, B> flatMap(Kind<List, A> fa, Function<A, Kind<List, B>> f) {
    List<A> list = fa.getRealType();
    List<B> result = ListInstance.getNewList(list);
    for (A a : list) {
      result.addAll(f.apply(a).getRealType());
    }
    return new ListKind<>(result);
  }
}
