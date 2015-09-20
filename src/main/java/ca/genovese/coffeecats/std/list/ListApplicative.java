package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.Applicative;
import ca.genovese.coffeecats.types.List;
import ca.genovese.coffeecats.util.Kind;

import java.util.function.Function;

public class ListApplicative implements Applicative<List> {
  @Override
  public <A> Kind<List, A> pure(A a) {
    return List.create(a);
  }

  @Override
  public <A, B> Kind<List, B> apply(Kind<List, A> fa, Kind<List, Function<A, B>> ff) {
    return applyHelper(fa, fa, ff);
  }


  private <A, B> Kind<List, B> applyHelper(Kind<List, A> full, Kind<List, A> fa, Kind<List, Function<A, B>> ff) {
    if (ff instanceof List.Nil) {
      return new List.Nil<>();
    } else if (fa instanceof List.Nil) {
      return applyHelper(full, full, (List<Function<A, B>>) ff.getRealType().getTail());
    } else {
      return new List.Cons<>(((Function<A, B>) ff.getRealType().getHead()).apply((A) fa.getRealType().getHead()),
          (List<B>) applyHelper(full, (List<A>) fa.getRealType().getTail(), (List<Function<A, B>>) ff));
    }
  }

}
