package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.SemigroupK;
import ca.genovese.coffeecats.util.Kind;
import ca.genovese.coffeecats.util.ListKind;

import java.util.ArrayList;
import java.util.List;

public interface ListSemigroupK extends SemigroupK<List> {
  ListSemigroupK instance = new ListSemigroupK() {
  };

  @Override
  default <A> Kind<List, A> combine(Kind<List, A> x, Kind<List, A> y) {
    ArrayList<A> as = new ArrayList<A>(x.getRealType().size() + y.getRealType().size());
    as.addAll(x.getRealType());
    as.addAll(y.getRealType());
    return new ListKind<>(as);
  }
}
