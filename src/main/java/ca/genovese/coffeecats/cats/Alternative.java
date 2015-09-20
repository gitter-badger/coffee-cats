package ca.genovese.coffeecats.cats;

import ca.genovese.coffeecats.util.Kind;

public interface Alternative<F extends Kind> extends Applicative<F>, MonoidK<F> {
}
