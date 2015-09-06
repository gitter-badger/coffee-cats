package ca.genovese.coffeecats.examples.option;

import ca.genovese.coffeecats.structures.HigherKind;

public interface Option<T> extends HigherKind<Option, T> {
  static <A> Option<A> create(A a) {
    if (a != null) {
      return new OptionSome<>(a);
    } else {
      return new OptionNone<>();
    }
  }

  T get();

  T getOrElse(T other);
}

