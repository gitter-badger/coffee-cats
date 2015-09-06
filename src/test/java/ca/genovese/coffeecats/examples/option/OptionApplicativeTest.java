package ca.genovese.coffeecats.examples.option;

import ca.genovese.coffeecats.examples.list.ListFunctor;
import ca.genovese.coffeecats.structures.Functor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class OptionApplicativeTest {
  OptionApplicative optionApplicative = new OptionApplicative();

  @Test
  public void testApplicativeIdentityLawSome() {
    Option<Integer> i = Option.create(1);
    Function<Integer, Integer> f = a -> a;
    assertEquals(optionApplicative.apply(i, optionApplicative.pure(f)), i);
  }

  @Test
  public void testIdentityLawNone() {
    Option<Integer> i = Option.create(null);
    assertEquals(optionApplicative.map(i, a -> a), i);
  }

  @Test
  public void testLift() {
    Option<Integer> i = Option.create(1);
    Function<Option, Option> f = optionApplicative.lift(Object::toString);
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

    Functor<List> composedFunctor = new ListFunctor().compose(optionApplicative);

    List<Option<Integer>> map = composedFunctor.map(l, a -> (Integer) a + 1);
    assertEquals(map, l2);
  }


}
