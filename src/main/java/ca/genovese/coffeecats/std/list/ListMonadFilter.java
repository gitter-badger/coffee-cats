package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.MonadFilter;
import ca.genovese.coffeecats.util.Kind;
import ca.genovese.coffeecats.util.ListKind;

import java.util.ArrayList;
import java.util.List;

public interface ListMonadFilter extends ListMonad, MonadFilter<List> {
  ListMonadFilter instance = new ListMonadFilter() {
  };

  default <A> Kind<List, A> empty() {
    return new ListKind<>(new ArrayList<>());
  }
}
