package ca.genovese.coffeecats.examples.option;

import ca.genovese.coffeecats.structures.Functor;
import ca.genovese.coffeecats.structures.FunctorLaws;
import ca.genovese.coffeecats.types.Option;
import ca.genovese.coffeecats.util.Kind;

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
  public Kind<Option, Integer> getRandomF() {
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

}
