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

  @Override
  public String toString() {
    return f.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }

    ListKind<?> listKind = (ListKind<?>) o;

    return f.equals(listKind.f);

  }

  @Override
  public int hashCode() {
    return f.hashCode();
  }
}
