package ca.genovese.coffeecats.cats;

import ca.genovese.coffeecats.util.Kind;

/**
 * Must obey the laws defined in [[laws.ComonadLaws]].
 */
public interface Comonad<F extends Kind> extends CoflatMap<F> {
  <A> A extract(Kind<F, A> fa);
}
