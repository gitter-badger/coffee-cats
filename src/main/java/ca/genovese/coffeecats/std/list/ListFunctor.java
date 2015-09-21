package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.Functor;
import ca.genovese.coffeecats.util.Kind;
import ca.genovese.coffeecats.util.ListKind;

import java.util.List;
import java.util.function.Function;

public interface ListFunctor extends Functor<List> {
  ListFunctor instance = new ListFunctor() {
  };

  @Override
  default <A, B> Kind<List, B> map(Kind<List, A> fa, Function<A, B> f) {
    List<A> list = fa.getRealType();
    List<B> result = ListInstance.getNewList(list);

    for (A a : list) {
      result.add(f.apply(a));
    }

    return new ListKind<>(result);
  }


}
