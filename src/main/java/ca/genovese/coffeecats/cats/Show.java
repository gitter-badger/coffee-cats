package ca.genovese.coffeecats.cats;

import java.util.function.Function;

public interface Show<T> {
  /** creates an instance of [[Show]] using the provided function */
  static <A> Show<A> show(Function<A, String> f) {
    return new Show<A>() {
      public String show(A a) {
        return f.apply(a);
      }
    };
  }

  /** creates an instance of [[Show]] using object toString */
  static <A> Show<A> fromToString() {
    return a -> a.toString();
  }

  String show(T t);
}
