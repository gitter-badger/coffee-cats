package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.FlatMap;
import ca.genovese.coffeecats.cats.Monad;
import ca.genovese.coffeecats.laws.MonadLaws;
import ca.genovese.coffeecats.util.Kind;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

public class ListMonadTest extends ListApplicativeTest implements MonadLaws<Integer, Double, String, List> {
  @Override
  public Monad<List> getMonad() {
    return ListMonad.instance;
  }

  @Override
  @Test
  public void testMonadLeftIdentity() {
    monadLeftIdentity();
  }

  @Override
  @Test
  public void testMonadRightIdentity() {
    monadRightIdentity();
  }

  @Override
  public FlatMap<List> getFlatMap() {
    return ListMonad.instance;
  }

  @Override
  public Function<Integer, Kind<List, Double>> getRandomAFB() {
    Kind<List, Double> list = makeList(rnd()::nextDouble);
    return i -> list;
  }

  @Override
  public Function<Double, Kind<List, String>> getRandomBFC() {
    Kind<List, String> list = makeList(() -> Integer.toHexString(rnd().nextInt()));
    return d -> list;
  }

  @Override
  @Test
  public void testFlatMapAssociativity() {
    flatMapAssociativity();
  }

  @Override
  @Test
  public void testFlatMapConsistentApply() {
    flatMapConsistentApply();
  }
}
