package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.cats.Applicative;
import ca.genovese.coffeecats.laws.ApplicativeLaws;

import java.util.List;

public class ListApplicativeTest extends ListApplyTest implements ApplicativeLaws<Integer, Double, String, List> {
  @Override
  public Applicative<List> getApplicative() {
    return ListApplicative.instance;
  }

  @Override
  public void testApplicativeIdentity() {
    applicativeIdentity();
  }

  @Override
  public void testApplicativeHomomorphism() {
    applicativeHomomorphism();
  }

  @Override
  public void testApplicativeInterchange() {
    applicativeInterchange();
  }

  @Override
  public void testApplicativeMap() {
    applicativeMap();
  }

  @Override
  public void testApplicativeComposition() {
    applicativeComposition();
  }
}
