package net.stickycode.coercion;

public abstract class AbstractNoDefaultCoercion<T>
    implements Coercion<T> {

  @Override
  public boolean hasDefaultValue() {
    return false;
  }
  
  @Override
  public T getDefaultValue() {
    throw new UnsupportedOperationException("This coercion " + toString() + " does not have a default value");
  }
  
  @Override
  public String toString() {
    return getClass().getSimpleName();
  }
}
