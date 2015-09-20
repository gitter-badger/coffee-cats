package ca.genovese.coffeecats.laws;

public interface BaseLaw {

  default Integer getExecutionCount() {
    return 500;
  }

  default void execLaw(Runnable law) {
    for (int i = 0; i < getExecutionCount(); i++) {
      law.run();
    }
  }
}
