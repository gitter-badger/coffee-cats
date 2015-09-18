package ca.genovese.coffeecats.structures;

import ca.genovese.coffeecats.util.Kind;

public interface Alternative<F extends Kind> extends Applicative<F>, MonoidK<F> {
}
