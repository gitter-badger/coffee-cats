package ca.genovese.coffeecats.structures;

import java.util.function.UnaryOperator;

public abstract class Fold<A> {
  public abstract A complete(Lazy<A> la);

  public static final class Return<A> extends Fold<A> {
    public final A a;

    public Return(A a) {
      this.a = a;
    }

    @Override
    public A complete(Lazy<A> la) {
      return a;
    }
  }

  public static final class Continue<A> extends Fold<A> {
    public final UnaryOperator<A> f;

    public Continue(UnaryOperator<A> f) {
      this.f = f;
    }

    @Override
    public A complete(Lazy<A> la) {
      return f.apply(la.value());
    }
  }

  public static final class Pass<A> extends Fold<A> {
    @Override
    public A complete(Lazy<A> la) {
      return la.value();
    }
  }
}
