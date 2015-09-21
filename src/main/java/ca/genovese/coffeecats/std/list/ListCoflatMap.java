package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.CoflatMap;
import ca.genovese.coffeecats.util.Kind;
import ca.genovese.coffeecats.util.ListKind;

import java.util.List;
import java.util.function.Function;

public interface ListCoflatMap extends ListFunctor, CoflatMap<List> {
  ListCoflatMap instance = new ListCoflatMap() {
  };

  @Override
  default <A, B> Kind<List, B> coflatMap(Kind<List, A> fa, Function<Kind<List, A>, B> f) {
    List<B> result = ListInstance.getNewList(fa.getRealType());
    result.add(f.apply(fa));
    return new ListKind<>(result);
  }

}
