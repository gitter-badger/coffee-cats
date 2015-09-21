package ca.genovese.coffeecats.std.list;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public interface ListInstance extends ListMonadCombine, ListTraverse, ListCoflatMap {
  ListInstance instance = new ListInstance() {};

  static <B> List<B> getNewList(List list) {
    List<B> result;
    try {
      result = list.getClass().getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      result = new ArrayList<>(list.size());
    }
    return result;
  }
}
