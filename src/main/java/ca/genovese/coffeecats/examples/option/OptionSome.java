package ca.genovese.coffeecats.examples.option;

public class OptionSome<T> implements Option<T> {
  private final T t;

  public OptionSome(T t) {
    this.t = t;
  }

  @Override
  public T get() {
    return t;
  }

  @Override
  public T getOrElse(T other) {
    return t;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    OptionSome<?> that = (OptionSome<?>) o;

    return !(t != null ? !t.equals(that.t) : that.t != null);
  }

  @Override
  public int hashCode() {
    return t != null ? t.hashCode() : 0;
  }
}
