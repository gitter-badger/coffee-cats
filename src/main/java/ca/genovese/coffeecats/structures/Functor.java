package ca.genovese.coffeecats.structures;

import ca.genovese.coffeecats.util.Unit;
import java.util.function.Function;
import java.util.function.Supplier;

// laws
// identity - functor.map(a -> a).equals(functor)

// composition -
// given Function<A,B> f and Function<B,C> g
// (functor.map(f).map(g))
// .equals(functor.map(a -> g.apply(f.apply(a))

//public interface Functor<F<?>> {
public interface Functor<F> {
  //  <A, B> F<B> map(F<A> fa, Function<A,B> f);
  <A, B> F map(F fa, Function<A,B> f);

//  default <A,B> Function<F<A>, F<B>> lift (Function<A,B> f) {
  default <A,B> Function<F, F> lift (Function<A,B> f) {
    return (F fa) -> map(fa, f);
  }

//  default <B> F<B> as(F<A> fa, Supplier<B> b) {
  default <B> F as(F fa, Supplier<B> b) {
    return map(fa, a -> b.get());
  }

//  default F<Unit> voidMe(F<A> fa) {
  default F voidMe(F fa) {
    return as(fa, () -> Unit.instance);
  }

//  default <G> Functor<F<G<?>>> compose(Functor<G<?>> fg) {
  default <G> Functor<F> compose(Functor<G> fg) {
//    return new Functor<F<G<?>>>() {
    return new Functor<F>() {
      @Override
//      public <A, B> F<G<B>> map(F<G<A>> fga, Function<A, B> f) {
      public <A, B> F map(F fga, Function<A, B> f) {
        return Functor.this.map(fga, ga -> fg.map(((G)ga), f));
      }
    };
  }
}

