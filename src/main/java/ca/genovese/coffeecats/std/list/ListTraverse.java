package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.Applicative;
import ca.genovese.coffeecats.cats.Traverse;
import ca.genovese.coffeecats.util.Kind;

import java.util.List;
import java.util.function.Function;

public interface ListTraverse extends ListFoldable, ListFunctor, Traverse<List> {
  ListTraverse instance = new ListTraverse() {
  };

  @Override
  default <G, A, B> Kind<Kind<G, List>, B> traverse(Kind<List, A> fa, Function<A, Kind<G, B>> f, Applicative<G> g) {
    Kind<G, List<B>> gba = g.pure(ListInstance.<B>getNewList(fa.getRealType()));
    Kind<G, List<B>> gbb = foldLeft(fa, gba, (buf, a) -> g.map2(buf, f.apply(a), (List<B> x, B y) -> {
      x.add(y);
      return x;
    }));
    return (Kind<Kind<G, List>, B>) gbb;
  }

}
