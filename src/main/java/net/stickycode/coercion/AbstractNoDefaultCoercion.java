package net.stickycode.coercion;

public abstract class AbstractNoDefaultCoercion<T>
    implements Coercion<T> {

  @Override
  public boolean hasDefaultValue() {
    return false;
  }

  @Override
  public T getDefaultValue(CoercionTarget target) {
    throw new UnsupportedOperationException("This coercion " + toString() + " does not define a default value");
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }
}
