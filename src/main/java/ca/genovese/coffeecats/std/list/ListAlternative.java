package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.Alternative;

import java.util.List;

public interface ListAlternative extends ListApplicative, ListMonoidK, Alternative<List> {
  ListAlternative instance = new ListAlternative() {
  };
}
