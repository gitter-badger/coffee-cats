package ca.genovese.coffeecats.structures;

import ca.genovese.coffeecats.util.TriFunction;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Applicative<F> extends Functor<F> {
  <A> F pure(A a);

  <A, B> F apply(F fa, F fab);

  default <A, B, Z> F apply2(F fa, F fb, F ffabc) {
    return apply(fa,
        apply(fb,
            map(ffabc,
                new Function<BiFunction<A, B, Z>, Function<B, Function<A, Z>>>() {
                  @Override
                  public Function<B, Function<A, Z>> apply(BiFunction<A, B, Z> f) {
                    return b -> a -> f.apply(a, b);
                  }
                }
            )));
  }

  @Override
  default <A, B> F map(F fa, Function<A, B> f) {
    return apply(fa, pure(f));
  }

  default <A, B, Z> F map2(F fa, F fb, BiFunction<A, B, Z> f) {
    return apply(fa, map(fb,
        new Function<B, Function<A, Z>>() {
          @Override
          public Function<A, Z> apply(B b) {
            return (A a) -> f.apply(a, b);
          }
        }
    ));
  }

  default <A, B, C, Z> F map3(F fa, F fb, F fc, TriFunction<A, B, C, Z> f) {
    return apply(fa, map2(fb, fc,
        new BiFunction<B, C, Function<A, Z>>() {
          @Override
          public Function<A, Z> apply(B b, C c) {
            return (A a) -> f.apply(a, b, c);
          }
        }
    ));
  }

  default <G> Applicative<F> compose(Applicative<G> g) {
    return new Applicative<F>() {
      @Override
      public <A> F pure(A a) {
        return null;
      }

      @Override
      public <A, B> F apply(F fa, F fab) {
        return null;
      }
    };
  }

}
