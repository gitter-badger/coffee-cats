package ca.genovese.coffeecats.std.functionallist;

import ca.genovese.coffeecats.cats.Applicative;
import ca.genovese.coffeecats.types.FunctionalList;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

public class ListApplicative implements Applicative<FunctionalList> {
  @Override
  public <A> Kind<FunctionalList, A> pure(A a) {
    return FunctionalList.create(a);
  }

  @Override
  public <A, B> Kind<FunctionalList, B> apply(Kind<FunctionalList, A> fa, Kind<FunctionalList, Function<A, B>> ff) {
    return applyHelper(fa, fa, ff);
  }


  private <A, B> Kind<FunctionalList, B> applyHelper(Kind<FunctionalList, A> full, Kind<FunctionalList, A> fa, Kind<FunctionalList, Function<A, B>> ff) {
    if (ff instanceof FunctionalList.Nil) {
      return new FunctionalList.Nil<>();
    } else if (fa instanceof FunctionalList.Nil) {
      return applyHelper(full, full, (FunctionalList<Function<A, B>>) ff.getRealType().getTail());
    } else {
      return new FunctionalList.Cons<>(((Function<A, B>) ff.getRealType().getHead()).apply((A) fa.getRealType().getHead()),
          (FunctionalList<B>) applyHelper(full, (FunctionalList<A>) fa.getRealType().getTail(), (FunctionalList<Function<A, B>>) ff));
    }
  }

}
