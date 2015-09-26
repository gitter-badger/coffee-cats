package ca.genovese.coffeecats.std.option;

import ca.genovese.coffeecats.cats.Functor;
import ca.genovese.coffeecats.laws.FunctorLaws;
import ca.genovese.coffeecats.types.Option;
import ca.genovese.coffeecats.util.Kind;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.Random;
import java.util.function.Function;

public class OptionFunctorTest implements FunctorLaws<Integer, Double, String, Option> {
  Random rnd = new SecureRandom();

  @Override
  public Functor<Option> getFunctor() {
    return new OptionFunctor();
  }

  @Override
  public Kind<Option, Integer> getRandomFA() {
    if (rnd.nextBoolean()) {
      return new Option.None<>();
    } else {
      return new Option.Some<>(rnd.nextInt());
    }
  }

  @Override
  public Function<Integer, Double> getRandomAB() {
    if (rnd.nextBoolean()) {
      return i -> i.doubleValue();
    } else {
      double v = rnd.nextDouble();
      return i -> i * v;
    }
  }

  @Override
  public Function<Double, String> getRandomBC() {
    if (rnd.nextBoolean()) {
      return d -> d.toString();
    } else {
      return d -> d.toString().concat(d.toString());
    }
  }

  @Override
  public Function<Double, Integer> getRandomBA() {
    if (true || rnd.nextBoolean()) {
      return d -> d.intValue();
    } else {
      int i = rnd.nextInt();
      return d -> d.intValue() * i;
    }
  }

  @Override
  public Function<String, Double> getRandomCB() {
    if (true || rnd.nextBoolean()) {
      return s -> Double.parseDouble(s);
    } else {
      double d = rnd.nextDouble();
      return s -> d;
    }
  }

  @Override
  @Test
  public void testInvariantIdentity() {
    invariantIdentity();
  }

  @Override
  @Test
  public void testInvariantComposition() {
    invariantComposition();
  }

  @Override
  @Test
  public void testCovariantIdentity() {
    covariantIdentity();
  }

  @Override
  @Test
  public void testCovariantComposition() {
    covariantComposition();
  }

}
