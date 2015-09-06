package ca.genovese.coffeecats.examples.list;

import ca.genovese.coffeecats.structures.Functor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ListFunctor implements Functor<List> {
  @Override
  public <A, B> List<B> map(List fa, Function<A, B> f) {
    List<A> la = (List<A>) fa;
    List<B> lb = new ArrayList<>(la.size());
    for (A a : la) {
      lb.add(f.apply(a));
    }
    return lb;
  }
}
