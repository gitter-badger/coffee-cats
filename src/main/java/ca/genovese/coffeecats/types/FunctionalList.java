package ca.genovese.coffeecats.types;

import ca.genovese.coffeecats.util.Kind;

import java.util.Iterator;

public interface FunctionalList<T> extends Kind<FunctionalList, T>, Iterable<T> {
  static <A> FunctionalList<A> create(A... as) {
    FunctionalList list = new Nil<>();

    for (int i = as.length - 1; i >= 0; i--) {
      list = new Cons<>(as[i], list);
    }

    return list;
  }

  T getHead();

  FunctionalList<T> getTail();

  class Cons<A> implements FunctionalList<A> {
    private final A head;
    private final FunctionalList<A> tail;

    public Cons(A head, FunctionalList<A> tail) {
      this.head = head;
      this.tail = tail;
    }

    public A getHead() {
      return head;
    }

    public FunctionalList<A> getTail() {
      return tail;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) { return true; }
      if (o == null || getClass() != o.getClass()) { return false; }

      Cons<?> listItem = (Cons<?>) o;

      if (head != null ? !head.equals(listItem.head) : listItem.head != null) { return false; }
      return !(tail != null ? !tail.equals(listItem.tail) : listItem.tail != null);
    }

    @Override
    public int hashCode() {
      int result = head != null ? head.hashCode() : 0;
      result = 31 * result + (tail != null ? tail.hashCode() : 0);
      return result;
    }

    @Override
    public String toString() {
      FunctionalList<A> t = this;
      StringBuilder sb = new StringBuilder("List {");
      for (A a : this) {
        sb.append(a.toString()).append(",");
      }
      sb.append("}");

      return sb.toString();
    }

    @Override
    public Iterator<A> iterator() {
      return new ListIterator<>(this);
    }

  }

  final class ListIterator<T> implements Iterator<T> {
    private FunctionalList<T> list;

    public ListIterator(FunctionalList<T> list) {
      this.list = list;
    }

    @Override
    public boolean hasNext() {
      return !(list instanceof Nil);
    }

    @Override
    public T next() {
      T head = list.getHead();
      list = list.getTail();
      return head;
    }
  }

  final class Nil<A> implements FunctionalList<A> {
    public A getHead() {
      throw new IllegalStateException("Head on an empty list");
    }

    public FunctionalList<A> getTail() {
      throw new IllegalStateException("Tail on an empty list");
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) { return true; }
      if (o == null || getClass() != o.getClass()) { return false; }
      return true;
    }

    @Override
    public int hashCode() {
      return 1;
    }

    @Override
    public Iterator<A> iterator() {
      return new Iterator<A>() {
        @Override
        public boolean hasNext() {
          return false;
        }

        @Override
        public A next() {
          throw new UnsupportedOperationException();
        }
      };
    }
  }
}
