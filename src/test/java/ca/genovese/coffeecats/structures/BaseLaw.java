package ca.genovese.coffeecats.structures;

import ca.genovese.coffeecats.util.Kind;

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
