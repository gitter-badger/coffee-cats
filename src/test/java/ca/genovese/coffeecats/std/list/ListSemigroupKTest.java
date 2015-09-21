package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.Functor;
import ca.genovese.coffeecats.cats.SemigroupK;
import ca.genovese.coffeecats.laws.FunctorLaws;
import ca.genovese.coffeecats.laws.SemigroupKLaws;
import ca.genovese.coffeecats.util.Kind;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

public class ListSemigroupKTest extends BaseListTest implements SemigroupKLaws<Integer, List> {

  @Override
  public SemigroupK<List> getSemigroupK() {
    return ListSemigroupK.instance;
  }

  @Override
  public Kind<List, Integer> getRandomFA() {
    return makeList(this::getRandomA);
  }

  @Override
  @Test
  public void testSemigroupKAssociative() {
    semigroupKAssociative();
  }
}
