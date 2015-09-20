package ca.genovese.coffeecats.cats;

import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

/**
 * Traverse, also known as Traversable.
 * <p/>
 * Traversal over a structure with an effect.
 * <p/>
 * Traversing with the [[cats.Id]] effect is equivalent to [[cats.Functor]]#map.
 * Traversing with the [[cats.data.Const]] effect where the first type parameter has
 * a [[cats.Monoid]] instance is equivalent to [[cats.Foldable]]#fold.
 * <p/>
 * See: [[https://www.cs.ox.ac.uk/jeremy.gibbons/publications/iterator.pdf The Essence of the Iterator Pattern]]
 */
public interface Traverse<F extends Kind> extends Functor<F>, Foldable<F> {

  /**
   * given a function which returns a G effect, thread this effect
   * through the running of this function on all the values in F,
   * returning an F[A] in a G context
   */
  <G extends Kind, A, B> Kind<Kind<G, F>, B> traverse(Kind<F, A> fa, Function<A, Kind<G, B>> f, Applicative<G> g);

  /**
   * thread all the G effects through the F structure to invert the
   * structure from F[G[_]] to G[F[_]]
   */
  default <G extends Kind, A> Kind<Kind<G, F>, A> sequence(Kind<F, Kind<G, A>> fga, Applicative<G> g) {
    return traverse(fga, Function.identity(), g);
  }
}
