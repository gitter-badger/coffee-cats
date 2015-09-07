package ca.genovese.coffeecats.structures;

import ca.genovese.coffeecats.structures.composits.CompositeApplicative;
import ca.genovese.coffeecats.util.Kind;
import ca.genovese.coffeecats.util.types.function.Function3;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Applicative<F extends Kind> extends Apply<F> {
  <A> Kind<F, A> pure(A a);

  @Override
  default <A, B> Kind<F, B> map(Kind<F, A> fa, Function<A, B> f) {
    return apply(fa, pure(f));
  }

  default <A, B, Z> Kind<F, Z> map2(Kind<F, A> fa, Kind<F, B> fb, BiFunction<A,
      B, Z> f) {
    return apply(fa, map(fb, b -> a -> f.apply(a, b)));
  }

  default <A, B, C, Z> Kind<F, Z> map3(Kind<F, A> fa, Kind<F, B> fb,
                                             Kind<F, C> fc, Function3<A, B, C, Z> f) {
    return apply(fa, map2(fb, fc, (b,c) -> a -> f.apply(a, b, c)));
  }


  default <G extends Kind<G, ?>> Applicative<Kind<F, G>> compose(Applicative<G> g) {
    return new CompositeApplicative<>(this, g);
  }
}
