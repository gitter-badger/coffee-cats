package ca.genovese.coffeecats.util;

public class WrapperKind<F, A> implements Kind<F, A> {
  private final F f;

  public WrapperKind(F f) {
    this.f = f;
  }

  public F getRealType() {
    return f;
  }
}
