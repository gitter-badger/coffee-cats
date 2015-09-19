package ca.genovese.coffeecats.structures;

import ca.genovese.coffeecats.util.Kind;

public interface Bimonad<F extends Kind> extends Comonad<F>, Monad<F> {
}
