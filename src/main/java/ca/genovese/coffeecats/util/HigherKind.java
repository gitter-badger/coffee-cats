package ca.genovese.coffeecats.util;

public interface HigherKind<F, A> {
  default F getRealType() {
    return (F) this;
  }
}
