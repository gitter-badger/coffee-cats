package ca.genovese.coffeecats.structures;

import ca.genovese.coffeecats.types.Option;
import ca.genovese.coffeecats.util.Kind;
import ca.genovese.coffeecats.util.Unit;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Data structures that can be folded to a summary value.
 * <p/>
 * In the case of a collection (such as `List` or `Set`), these
 * methods will fold together (combine) the values contained in the
 * collection to produce a single result. Most collection types have
 * `foldLeft` methods, which will usually be used by the associationed
 * `Fold[_]` instance.
 * <p/>
 * Foldable[F] is implemented in terms of two basic methods:
 * <p/>
 * - `foldLeft(fa, b)(f)` eagerly folds `fa` from left-to-right.
 * - `foldLazy(fa, b)(f)` lazily folds `fa` from right-to-left.
 * <p/>
 * Beyond these it provides many other useful methods related to
 * folding over F[A] values.
 * <p/>
 * See: [[https://www.cs.nott.ac.uk/~gmh/fold.pdf A tutorial on the universality and expressiveness of fold]]
 */
public interface Foldable<F extends Kind> {
  /**
   * Left associative fold on 'F' using the function 'f'.
   */
  <A, B> B foldLeft(Kind<F, A> fa, B b, BiFunction<B, A, B> f);

  /**
   * Low-level method that powers `foldLazy`.
   */
  <A, B> Fold<B> partialFold(Kind<F, A> fa, Function<A, Fold<B>> f);

  /**
   * Right associative lazy fold on `F` using the folding function 'f'.
   * <p/>
   * This method evaluates `b` lazily (in some cases it will not be
   * needed), and returns a lazy value. We are using `A => Fold[B]` to
   * support laziness in a stack-safe way.
   * <p/>
   * For more detailed information about how this method works see the
   * documentation for `Fold[_]`.
   */
  default <A, B> Lazy<B> foldLazy(Kind<F, A> fa, Lazy<B> lb, Function<A, Fold<B>> f) {
    return Lazy.byNeed(() -> partialFold(fa, f).complete(lb));
  }

  /**
   * Right associative fold on 'F' using the function 'f'.
   * <p/>
   * The default implementation is written in terms of
   * `foldLazy`. Most instances will want to override this method for
   * performance reasons.
   */
  default <A, B> B foldRight(Kind<F, A> fa, B b, BiFunction<A, B, B> f) {
    return foldLazy(fa, Lazy.eager(b), a -> new Fold.Continue<>(bb -> f.apply(a, bb))).value();
  }

  /**
   * Fold implemented using the given Monoid[A] instance.
   */
  default <A> A fold(Kind<F, A> fa, Monoid<A> ma) {
    return foldLeft(fa, ma.empty(), (acc, a) -> ma.combine(acc, a));
  }

  /**
   * Fold implemented by mapping `A` values into `B` and then
   * combining them using the given `Monoid[B]` instance.
   */
  default <A, B> B foldMap(Kind<F, A> fa, Function<A, B> f, Monoid<B> mb) {
    return foldLeft(fa, mb.empty(), (b, a) -> mb.combine(b, f.apply(a)));
  }

  /**
   * Traverse `F[A]` using `Applicative[G]`.
   * <p/>
   * `A` values will be mapped into `G[B]` and combined using
   * `Applicative#map2`.
   * <p/>
   * For example:
   * {{{
   * def parseInt(s: String): Option[Int] = ...
   * val F = Foldable[List]
   * F.traverse_(List("333", "444"))(parseInt) // Some(())
   * F.traverse_(List("333", "zzz"))(parseInt) // None
   * }}}
   * <p/>
   * This method is primarily useful when `G[_]` represents an action
   * or effect, and the specific `A` aspect of `G[A]` is not otherwise
   * needed.
   */
  default <G extends Kind, A, B> Kind<G, Unit> traverse_(Kind<F, A> fa, Function<A, Kind<G, B>> f, Applicative<G> G) {
    return foldLeft(fa, G.pure(Unit.instance), (acc, a) -> G.map2(acc, f.apply(a), (u, b) -> Unit.instance));
  }

  /**
   * Sequence `F[G[A]]` using `Applicative[G]`.
   * <p/>
   * This is similar to `traverse_` except it operates on `F[G[A]]`
   * values, so no additional functions are needed.
   * <p/>
   * For example:
   * <p/>
   * {{{
   * val F = Foldable[List]
   * F.sequence_(List(Option(1), Option(2), Option(3))) // Some(())
   * F.sequence_(List(Option(1), None, Option(3)))      // None
   * }}}
   */
  default <G extends Kind, A> Kind<G, Unit> sequence_(Kind<F, Kind<G, A>> fga, Applicative<G> G) {
    return traverse_(fga, Function.identity(), G);
  }

  /**
   * Fold implemented using the given `MonoidK[G]` instance.
   * <p/>
   * This method is identical to fold, except that we use the universal monoid (`MonoidK[G]`)
   * to get a `Monoid[G[A]]` instance.
   * <p/>
   * For example:
   * <p/>
   * {{{
   * val F = Foldable[List]
   * F.foldK(List(1 :: 2 :: Nil, 3 :: 4 :: 5 :: Nil))
   * // List(1, 2, 3, 4, 5)
   * }}}
   */
  default <G extends Kind, A> Kind<G, A> foldK(Kind<F, Kind<G, A>> fga, MonoidK<G> G) {
    Monoid<Kind<G, A>> algebra = G.algebra();
    return fold(fga, algebra);
  }

  /**
   * find the first element matching the predicate, if one exists
   */
  default <A> Option<A> find(Kind<F, A> fa, Predicate<A> p) {
    return foldLazy(fa, Lazy.eager(new Option.None<A>()), (A a) -> {
      if (p.test(a)) {
        return new Fold.Return<>(new Option.Some<>(a));
      } else {
        return new Fold.Pass<>();
      }
    }).value();
  }

}
