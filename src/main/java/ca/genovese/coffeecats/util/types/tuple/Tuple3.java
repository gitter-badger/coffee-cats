package ca.genovese.coffeecats.util.types.tuple;

public class Tuple3<A, B, C> {
  public final A a;
  public final B b;
  public final C c;

  public Tuple3(A a, B b, C c) {
    this.a = a;
    this.b = b;
    this.c = c;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Tuple3<?, ?, ?> tuple3 = (Tuple3<?, ?, ?>) o;

    if (a != null ? !a.equals(tuple3.a) : tuple3.a != null) return false;
    if (b != null ? !b.equals(tuple3.b) : tuple3.b != null) return false;
    return !(c != null ? !c.equals(tuple3.c) : tuple3.c != null);

  }

  @Override
  public int hashCode() {
    int result = a != null ? a.hashCode() : 0;
    result = 31 * result + (b != null ? b.hashCode() : 0);
    result = 31 * result + (c != null ? c.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "(" + a + ", " + b + ", " + c + ")";
  }
}
