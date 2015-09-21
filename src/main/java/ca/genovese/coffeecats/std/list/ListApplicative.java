package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.Applicative;
import ca.genovese.coffeecats.util.Kind;
import ca.genovese.coffeecats.util.ListKind;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public interface ListApplicative extends ListApply, Applicative<List> {
  ListApplicative instance = new ListApplicative() {
  };

  @Override
  default <A> Kind<List, A> pure(A a) {
    ArrayList<A> as = new ArrayList<>();
    as.add(a);
    return new ListKind<>(as);
  }

  @Override
  default <A, B> Kind<List, B> map(Kind<List, A> fa, Function<A, B> f) {
    return ListApply.super.map(fa, f);
  }
}
