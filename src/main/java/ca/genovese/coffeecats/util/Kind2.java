package ca.genovese.coffeecats.util;

public interface Kind2<F, A, B> {
  default F getRealType() {
    return (F) this;
  }

  interface Kind2Doubled<F, A> extends Kind2<F, A, A>, Kind<F, A> {
    default F getRealType() {
      return (F) this;
    }
  }
}

