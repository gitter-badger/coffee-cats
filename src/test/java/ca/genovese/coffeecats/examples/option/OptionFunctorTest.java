package ca.genovese.coffeecats.examples.option;

import static org.junit.Assert.assertEquals;

import ca.genovese.coffeecats.types.List;
import ca.genovese.coffeecats.examples.list.ListFunctor;
import ca.genovese.coffeecats.structures.Functor;
import ca.genovese.coffeecats.util.HigherKind;
import ca.genovese.coffeecats.types.Option;
import java.util.function.Function;
import org.junit.Test;

public class OptionFunctorTest {
  Functor<Option> optionFunctor;

  public OptionFunctorTest() {
    optionFunctor = new OptionFunctor();
  }

  @Test
  public void testIdentityLawSome() {
    Option<Integer> i = Option.create(1);
    assertEquals(optionFunctor.map(i, a -> a).getRealType(), i);
  }

  @Test
  public void testIdentityLawNone() {
    Option<Integer> i = Option.create(null);
    assertEquals(optionFunctor.map(i, a -> a).getRealType(), i);
  }

  @Test
  public void testLift() {
    Option<Integer> i = Option.create(1);
    Function<HigherKind<Option, Integer>, HigherKind<Option, String>> f =
        optionFunctor.lift(a -> a.toString());

    assertEquals(f.apply(i).getRealType(), Option.create("1"));
  }

  @Test
  public void testCompose() {
    HigherKind<HigherKind<List, Option>, Integer> l =
        (HigherKind<HigherKind<List, Option>, Integer>) (Object) List.create(
            Option.create(1),
            Option.create(null),
            Option.create(3));

    List<Option<Integer>> l2 = List.create(
        Option.create(2),
        Option.create(null),
        Option.create(4));

    HigherKind<HigherKind<List, Option>, Integer> map = new ListFunctor()
        .compose(optionFunctor)
        .map(l, a -> a + 1);

    assertEquals(l2, map);
  }


}
