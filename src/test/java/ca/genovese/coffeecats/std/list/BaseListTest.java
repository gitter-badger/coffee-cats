package ca.genovese.coffeecats.std.list;

import ca.genovese.coffeecats.util.Kind;
import ca.genovese.coffeecats.util.ListKind;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class BaseListTest {
  private Random rnd = new SecureRandom();

  public Random rnd() {
    return rnd;
  }

  protected <A> Kind<List, A> makeList(Supplier<A> s) {
    List<A> list = new ArrayList<>();
    int size = rnd().nextInt(50);
    for (int i = 0; i < size; i++) {
      list.add(s.get());
    }
    return new ListKind<>(list);
  }

  public Integer getRandomA() {
    return rnd().nextInt();
  }
}
