package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.Alternative;
import ca.genovese.coffeecats.cats.FlatMap;
import ca.genovese.coffeecats.cats.Monad;
import ca.genovese.coffeecats.cats.MonoidK;
import ca.genovese.coffeecats.laws.AlternativeLaws;
import ca.genovese.coffeecats.laws.MonadLaws;
import ca.genovese.coffeecats.util.Kind;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

public class ListAlternativeTest extends ListApplicativeTest implements AlternativeLaws<Integer, Double, String, List> {
  @Override
  public Alternative<List> getAlternative() {
    return ListAlternative.instance;
  }

  @Override
  @Test
  public void testAlternativeRightAbsorption() {
    alternativeRightAbsorption();
  }

  @Override
  @Test
  public void testAlternativeLeftDistributivity() {
    alternativeLeftDistributivity();
  }

  @Override
  @Test
  public void testAlternativeRightDistributivity() {
    alternativeRightDistributivity();
  }

  @Override
  @Test
  public void testMonoidKLeftIdentity() {
    monoidKLeftIdentity();
  }

  @Override
  @Test
  public void testMonoidKRightIdentity() {
    monoidKRightIdentity();
  }

  @Override
  @Test
  public void testSemigroupKAssociative() {
    semigroupKAssociative();
  }
}
