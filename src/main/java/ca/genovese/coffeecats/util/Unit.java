package ca.genovese.coffeecats.util;

import java.util.function.Consumer;
import java.util.function.Function;

public final class Unit {
  public static final Unit instance = new Unit();
  public static <A> Function<A, Unit> fromConsumer(Consumer<A> f) {
    return a -> {f.accept(a); return instance;};
  }
}
