package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.MonadCombine;
import ca.genovese.coffeecats.cats.MonadFilter;
import ca.genovese.coffeecats.laws.MonadCombineLaws;
import ca.genovese.coffeecats.laws.MonadFilterLaws;
import org.junit.Test;

import java.util.List;

public class ListMonadCombineTest extends ListMonadFilterTest
    implements MonadCombineLaws<Integer, Double, String, List> {
  @Override
  public MonadCombine<List> getMonadCombine() {
    return ListMonadCombine.instance;
  }

  @Override
  public void testMonadCombineLeftDistributivity() {
    monadCombineLeftDistributivity();
  }
}
