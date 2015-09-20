package ca.genovese.coffeecats.cats;

import ca.genovese.coffeecats.std.list.ListFoldable;
import ca.genovese.coffeecats.util.ListKind;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
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

  public static <A, B> Fold<B> partialIterate(Iterable<A> as, Function<A, Fold<B>> f) {
    BiFunction<B, List<Function<B, B>>, B> unroll = (B b, List<Function<B, B>> fs) ->
      ListFoldable.instance.foldLeft(fs, b, (bb, ff) -> ff.apply(bb));

    List<Function<B, B>> fs = new ArrayList<>();

    for (A a : as) {
      Fold<B> next = f.apply(a);
      if (next instanceof Return) {
        B b = ((Return<B>) next).a;
        return new Return<>(unroll.apply(b, fs));
      } else if (next instanceof Continue) {
        fs.add(0, ((Continue) next).f);
      }
    }
    return new Continue<>(b -> unroll.apply(b, fs));
  }

  /*
    def partialIterate[A, B](as: Iterable[A])(f: A => Fold[B]): Fold[B] = {
    def unroll(b: B, fs: List[B => B]): B =
      fs.foldLeft(b)((b, f) => f(b))
    def loop(it: Iterator[A], fs: List[B => B]): Fold[B] =
      if (it.hasNext) {
        f(it.next) match {
          case Return(b) => Return(unroll(b, fs))
          case Continue(f) => loop(it, f :: fs)
          case _ => loop(it, fs)
        }
      } else Continue(b => unroll(b, fs))
    loop(as.iterator, Nil)
  }
   */
}
