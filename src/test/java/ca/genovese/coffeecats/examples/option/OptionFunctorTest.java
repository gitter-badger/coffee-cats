package ca.genovese.coffeecats.examples.option;

import static org.junit.Assert.assertEquals;

import ca.genovese.coffeecats.examples.list.ListFunctor;
import ca.genovese.coffeecats.structures.Functor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.junit.Test;

public class OptionFunctorTest {
  OptionFunctor optionFunctor = new OptionFunctor();

  @Test
  public void testIdentityLawSome() {
    Option<Integer> i = Option.create(1);
    assertEquals(optionFunctor.map(i, a -> a), i);
  }

  @Test
  public void testIdentityLawNone() {
    Option<Integer> i = Option.create(null);
    assertEquals(optionFunctor.map(i, a -> a), i);
  }

  @Test
  public void testLift() {
    Option<Integer> i = Option.create(1);
    Function<Option, Option> f = optionFunctor.lift(a -> a.toString());
    assertEquals(f.apply(i), Option.create("1"));
  }

  @Test
  public void testCompose() {
    List<Option<Integer>> l = new ArrayList<>();
    l.add(Option.create(1));
    l.add(Option.create(null));
    l.add(Option.create(3));

    List<Option<Integer>> l2 = new ArrayList<>();
    l2.add(Option.create(2));
    l2.add(Option.create(null));
    l2.add(Option.create(4));

    Functor<List> composedFunctor = new ListFunctor().compose(optionFunctor);

    List<Option<Integer>> map = composedFunctor.map(l, a -> (Integer) a + 1);
    assertEquals(map, l2);
  }


}
