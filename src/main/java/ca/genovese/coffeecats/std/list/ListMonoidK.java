package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.MonoidK;
import ca.genovese.coffeecats.util.Kind;
import ca.genovese.coffeecats.util.ListKind;

import java.util.ArrayList;
import java.util.List;

public interface ListMonoidK extends ListSemigroupK, MonoidK<List> {
  ListMonoidK instance = new ListMonoidK() {
  };

  @Override
  default <A> Kind<List, A> empty() {
    return new ListKind<>(new ArrayList<>());
  }
}
