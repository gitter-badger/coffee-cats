package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.FlatMap;
import ca.genovese.coffeecats.cats.Monad;
import ca.genovese.coffeecats.cats.MonadFilter;
import ca.genovese.coffeecats.laws.MonadFilterLaws;
import ca.genovese.coffeecats.laws.MonadLaws;
import ca.genovese.coffeecats.util.Kind;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

public class ListMonadFilterTest extends ListMonadTest implements MonadFilterLaws<Integer, Double, String, List> {
  @Override
  public MonadFilter<List> getMonadFilter() {
    return ListMonadFilter.instance;
  }

  @Override
  @Test
  public void testMonadFilterLeftEmpty() {
    monadFilterLeftEmpty();
  }

  @Override
  @Test
  public void testMonadFilterRightEmpty() {
    monadFilterRightEmpty();
  }
}
