package ca.genovese.coffeecats.util;

public interface Kind<F, A> {
  default F getRealType() {
    return (F) this;
  }
}
