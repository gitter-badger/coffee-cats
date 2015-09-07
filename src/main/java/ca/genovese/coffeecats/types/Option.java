package ca.genovese.coffeecats.types;

import ca.genovese.coffeecats.util.Kind;

public interface Option<T> extends Kind<Option, T> {
  static <A> Option<A> create(A a) {
    if (a != null) {
      return new Some<>(a);
    } else {
      return new None<>();
    }
  }

  T get();

  T getOrElse(T other);

  class None<T> implements Option<T> {
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
      if (this == o) { return true; }
      if (o == null || getClass() != o.getClass()) { return false; }
      return true;
    }

    @Override
    public int hashCode() {
      return 1;
    }

  }

  class Some<T> implements Option<T> {
    private final T t;

    public Some(T t) {
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
      if (this == o) { return true; }
      if (o == null || getClass() != o.getClass()) { return false; }

      Some<?> that = (Some<?>) o;

      return !(t != null ? !t.equals(that.t) : that.t != null);
    }

    @Override
    public String toString() {
      return "OptionSome{" +
          "t=" + t +
          '}';
    }

    @Override
    public int hashCode() {
      return t != null ? t.hashCode() : 0;
    }
  }
}

