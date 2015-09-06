package ca.genovese.coffeecats.examples.option;

public class OptionNone<T> implements Option<T> {
  @Override
  public T get() {
    throw new UnsupportedOperationException();
  }

  @Override
  public T getOrElse(T other) {
    return other;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    return true;
  }

  @Override
  public int hashCode() {
    return 1;
  }

}
