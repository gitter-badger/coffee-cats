package ca.genovese.coffeecats.structures;

import ca.genovese.coffeecats.util.HigherKind;
import ca.genovese.coffeecats.util.types.function.Function3;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Applicative<F extends HigherKind> extends Functor<F> {
  <A> HigherKind<F, A> pure(A a);

  <A, B> HigherKind<F, B> apply(HigherKind<F, A> fa, HigherKind<F, Function<A, B>> f);

  @Override
  default <A, B> HigherKind<F, B> map(HigherKind<F, A> fa, Function<A, B> f) {
    return apply(fa, pure(f));
  }

  default <A, B, Z> HigherKind<F, Z> apply2(HigherKind<F, A> fa, HigherKind<F, B> fb,
                                            HigherKind<F, BiFunction<A, B, Z>> f) {
    return apply(fa,
        apply(fb,
            map(f,
                new Function<BiFunction<A, B, Z>, Function<B, Function<A, Z>>>() {
                  @Override
                  public Function<B, Function<A, Z>> apply(BiFunction<A, B, Z> f) {
                    return b -> a -> f.apply(a, b);
                  }
                }
            )));
  }


  default <A, B, Z> HigherKind<F, Z> map2(HigherKind<F, A> fa, HigherKind<F, B> fb, BiFunction<A,
      B, Z> f) {
    return apply(fa, map(fb,
        new Function<B, Function<A, Z>>() {
          @Override
          public Function<A, Z> apply(B b) {
            return (A a) -> f.apply(a, b);
          }
        }
    ));
  }

  default <A, B, C, Z> HigherKind<F, Z> map3(HigherKind<F, A> fa, HigherKind<F, B> fb,
                                             HigherKind<F, C> fc, Function3<A, B, C, Z> f) {
    return apply(fa, map2(fb, fc,
        new BiFunction<B, C, Function<A, Z>>() {
          @Override
          public Function<A, Z> apply(B b, C c) {
            return (A a) -> f.apply(a, b, c);
          }
        }
    ));
  }

  default <A, B> Function<HigherKind<F, A>, HigherKind<F, B>> flip(HigherKind<F, Function<A, B>>
                                                                       f) {
    return fa -> apply(fa, f);
  }

  default <G extends HigherKind<G, ?>> Functor<HigherKind<F, G>> compose(Applicative<G> g) {
    return new Applicative<HigherKind<F, G>>() {
      @Override
      public <A> HigherKind<HigherKind<F, G>, A> pure(A a) {
        return (HigherKind<HigherKind<F, G>, A>) (Object) Applicative.this.pure(g.pure(a));
      }

      @Override
      public <A, B> HigherKind<HigherKind<F, G>, B> apply(HigherKind<HigherKind<F, G>, A> fga,
                                                          HigherKind<HigherKind<F, G>,
                                                              Function<A, B>> ff) {

        HigherKind<F, HigherKind<G, A>> f_ga = (HigherKind<F, HigherKind<G, A>>) fga;
        HigherKind<F, HigherKind<G, Function<A, B>>> ff2 =
            (HigherKind<F, HigherKind<G, Function<A, B>>>) fga;

        HigherKind<F, Function<HigherKind<G, A>, HigherKind<G, B>>> x =
            Applicative.this.map(ff2, gab -> g.flip(gab));
        return (HigherKind<HigherKind<F, G>, B>) (Object) Applicative.this.apply(f_ga, x);
      }
    };
  }
}
