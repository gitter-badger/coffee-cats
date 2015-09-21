package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.MonoidK;
import ca.genovese.coffeecats.laws.MonoidKLaws;
import org.junit.Test;

import java.util.List;

public class ListMonoidKTest extends ListSemigroupKTest implements MonoidKLaws<Integer, List> {
  @Override
  public MonoidK<List> getMonoidK() {
    return ListMonoidK.instance;
  }

  @Override
  public void testMonoidKLeftIdentity() {
    monoidKLeftIdentity();
  }

  @Override
  @Test
  public void testMonoidKRightIdentity() {
    monoidKRightIdentity();
  }
}
