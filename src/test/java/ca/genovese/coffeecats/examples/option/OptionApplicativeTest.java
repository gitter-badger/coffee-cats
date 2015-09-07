package ca.genovese.coffeecats.examples.option;

import ca.genovese.coffeecats.structures.Applicative;
import ca.genovese.coffeecats.structures.Functor;
import ca.genovese.coffeecats.types.Option;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class OptionApplicativeTest extends OptionFunctorTest {
  private Applicative<Option> optionApplicative = new OptionApplicative();
  private Functor<Option> optionFunctor = optionApplicative;

  public OptionApplicativeTest() {
    optionApplicative = new OptionApplicative();
    optionFunctor = optionApplicative;
  }

  @Test
  public void testApplicativeIdentityLawSome() {
    Option<Integer> i = Option.create(1);
    assertEquals(optionApplicative.apply(i, optionApplicative.pure(a -> a)).getRealType(), i);
  }

  @Test
  public void testApplicativeIdentityLawNone() {
    Option<Integer> i = Option.create(null);
    assertEquals(optionApplicative.apply(i, optionApplicative.pure(a -> a)).getRealType(), i);
  }

  @Test
  public void testApplicativeHomomorphismLawSome() {
    Integer i = 1;
    Function<Integer, String> f = a -> a.toString();
    assertEquals(optionApplicative.apply(optionApplicative.pure(i), optionApplicative.pure(f)),
        optionApplicative.pure(f.apply(i)));
  }

  @Test
  public void testApplicativeInterchangeLawSome() {
    Integer i = 1;
    Option<Function<Integer, String>> ff = Option.create(a -> a.toString());

    assertEquals(optionApplicative.apply(optionApplicative.pure(i), ff),
        optionApplicative.apply(ff, optionApplicative.pure(f -> f.apply(i))));
  }

  @Test
  public void testApplicativeMapLawSome() {
    Integer i = 1;
    Function<Integer, String> f = a -> a.toString();

    assertEquals(optionApplicative.map(optionApplicative.pure(i), f),
        optionApplicative.apply(optionApplicative.pure(i), optionApplicative.pure(f)));
  }
}
