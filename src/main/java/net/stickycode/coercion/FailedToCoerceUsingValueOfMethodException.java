package net.stickycode.coercion;

import java.lang.reflect.InvocationTargetException;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class FailedToCoerceUsingValueOfMethodException
    extends PermanentException {

  public FailedToCoerceUsingValueOfMethodException(InvocationTargetException e, Class<?> type, String value) {
    super(e, "Failed to invoke value of method on '' with value ''", type.getName(), value);
  }

}
