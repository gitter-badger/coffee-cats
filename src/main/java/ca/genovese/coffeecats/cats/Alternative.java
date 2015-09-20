package ca.genovese.coffeecats.cats;

import ca.genovese.coffeecats.util.Kind;

public interface Alternative<F> extends Applicative<F>, MonoidK<F> {
}
