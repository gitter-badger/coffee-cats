package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.MonadCombine;
import ca.genovese.coffeecats.util.Kind;

import java.util.List;

public interface ListMonadCombine extends ListMonadFilter, ListAlternative, MonadCombine<List> {
  ListMonadCombine instance = new ListMonadCombine() {
  };

  @Override
  default <A> Kind<List, A> empty() {
    return ListMonadFilter.super.empty();
  }
}
