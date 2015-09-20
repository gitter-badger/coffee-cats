package ca.genovese.coffeecats.cats;

import java.util.function.Supplier;

/**
 * Represents a value which may not yet be evaluated.
 *
 * Lazy provides a method to abstract across the evaluation strategy.
 *
 * There are three supported strategies:
 *
 *  - `Lazy.ByNeed(...)`: call-by-need semantics; the value of `...` will not
 *     be calculated until needed, but will be calculated at most once
 *     (and saved via memoization).
 *
 *  - `Lazy.eager(...)`: call-by-value semantics; the value of `...`
 *    will be immediately calculated and saved. This is the default
 *    strategy used by Scala.
 *
 *  - `Lazy.byName(...)`: call-by-name semantics; the value of `...`
 *    will not be calculated until needed, and will be calculated
 *    every time it is needed.
 *
 * Every Lazy[A] value has (or can calculate) a corresponding A
 * value. You can obtain this value by calling the `.value()` method.
 */

public abstract class Lazy<A> {
  /**
   * Obtain the underlying value from this lazy instance. If the value
   * has already been calculated, it will be returned. Otherwise, it
   * will be calculated and returned (and optionally memoized).
   */
  public abstract A value();

  /**
   * Given a lazy value, create a new one which will cache
   * (i.e. memoize) its value.
   *
   * The practical effect of this method is to convert by-name
   * instances to by-need (since eager instances already have a
   * memoized value).
   */
  public abstract Lazy<A> cached();

  /**
   * Given a lazy value, create a new one which will not cache its
   * value (forgetting a cached value if any).
   *
   * The practical effect of this method is to convert by-need
   * instances to by-name (eager instances have no way to recalculate
   * their value so they are unaffected).
   */
  public abstract Lazy<A> uncached();

  /**
   * Construct a lazy value.
   *
   * This instance will be call-by-need (`body` will not be evaluated
   * until needed).
   */
  public static <A> Lazy<A> byNeed(Supplier<A> a) {
    return new ByNeed<>(a);
  }

  /**
   * Construct a lazy value.
   *
   * This instance will be call-by-name (`body` will not be evaluated
   * until needed).
   */
  public static <A> Lazy<A> byName(Supplier<A> a) {
    return new ByName<>(a);
  }

  /**
   * Construct a lazy value.
   *
   * This instance will be call-by-value (`a` will immediately be
   * evaluated).
   */
  public static <A> Lazy<A> eager(Supplier<A> a) {
    return new Eager<>(a);
  }

  /**
   * Construct a lazy value.
   *
   * This instance will be call-by-value (`a` will have already been
   * evaluated).
   */
  public static <A> Lazy<A> eager(A a) {
    return new Eager<>(() -> a);
  }

  private static final class Eager<A> extends Lazy<A> {
    private final A a;

    public Eager(Supplier<A> a) {
      this.a = a.get();
    }

    @Override
    public A value() {
      return a;
    }

    @Override
    public Lazy<A> cached() {
      return this;
    }

    @Override
    public Lazy<A> uncached() {
      return this;
    }
  }

  private static final class ByName<A> extends Lazy<A> {
    public final Supplier<A> a;

    public ByName(Supplier<A> a) {
      this.a = a;
    }

    @Override
    public A value() {
      return a.get();
    }

    @Override
    public Lazy<A> cached() {
      return new ByNeed<>(a);
    }

    @Override
    public Lazy<A> uncached() {
      return this;
    }
  }

  private static final class ByNeed<A> extends Lazy<A> {
    public final Supplier<A> f;
    public A a;

    public ByNeed(Supplier<A> a) {
      this.f = a;
    }

    @Override
    public synchronized A value() {
      if (a == null) {
        a = f.get();
      }
      return a;
    }

    @Override
    public Lazy<A> cached() {
      return this;
    }

    @Override
    public Lazy<A> uncached() {
      return new ByName<>(f);
    }
  }
}
