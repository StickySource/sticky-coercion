package net.stickycode.coercion;

import java.lang.reflect.InvocationTargetException;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class FailedToCoerceUsingParseMethodException
    extends PermanentException {

  public FailedToCoerceUsingParseMethodException(InvocationTargetException e, Class<?> type, String value) {
    super(e, "Failed to invoke parse method on '' with value ''", type.getName(), value);
  }

}
