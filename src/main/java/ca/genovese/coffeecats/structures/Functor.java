package ca.genovese.coffeecats.structures;

import ca.genovese.coffeecats.util.HigherKind;
import ca.genovese.coffeecats.util.Unit;
import ca.genovese.coffeecats.util.types.tuple.Tuple2;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Functor<F extends HigherKind> {
  <A, B> HigherKind<F, B> map(HigherKind<F, A> fa, Function<A, B> f);

  default <A, B> Function<HigherKind<F, A>, HigherKind<F, B>> lift(Function<A, B> f) {
    return (HigherKind<F, A> fa) -> map(fa, f);
  }

  default <A, B> HigherKind<F, Tuple2<A, B>> zipWith(HigherKind<F, A> fa, Function<A, B> f) {
    return map(fa, a -> new Tuple2<>(a, f.apply(a)));
  }

  default <B> HigherKind<F, B> as(HigherKind<F, ?> fa, Supplier<B> b) {
    return map(fa, a -> b.get());
  }

  default HigherKind<F, Unit> voidMe(HigherKind<F, ?> fa) {
    return as(fa, () -> Unit.instance);
  }

  default <G extends HigherKind> Functor<HigherKind<F, G>> compose(Functor<G> fg) {
    return new Functor<HigherKind<F, G>>() {

      @Override
      public <A, B> HigherKind<HigherKind<F, G>, B> map(HigherKind<HigherKind<F, G>, A> fg_a,
                                                        Function<A, B> f) {
        HigherKind<F, HigherKind<G, A>> f_ga = (HigherKind<F, HigherKind<G, A>>) fg_a;
        HigherKind<F, HigherKind<G, B>> mapResult_f_gb = cmap(f_ga, f);
        Object mapResult_o = (Object) mapResult_f_gb;
        HigherKind<HigherKind<F, G>, B> mapResult_fg_b = (HigherKind<HigherKind<F, G>, B>)
            mapResult_o;
        return mapResult_fg_b;
      }

      public <A, B> HigherKind<F, HigherKind<G, B>> cmap(HigherKind<F, HigherKind<G, A>> fga,
                                                         Function<A, B> f) {
        return Functor.this.map(fga, ga -> fg.map(((G) ga), f));
      }
    };
  }
}

