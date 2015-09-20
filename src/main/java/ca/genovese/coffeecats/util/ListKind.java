package ca.genovese.coffeecats.util;

import java.util.List;

public class ListKind<A> implements Kind<List, A> {
  private final List<A> f;

  public ListKind(List<A> f) {
    this.f = f;
  }

  public List<A> getRealType() {
    return f;
  }
}
