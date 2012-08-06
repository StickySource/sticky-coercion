package net.stickycode.coercion;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class UnableToCoerceAsCharsetIsNotSupported
    extends PermanentException {

  public UnableToCoerceAsCharsetIsNotSupported(CoercionTarget target, String characterSetName) {
    super("Failed to coerce {} as character set {} is not suppoerted", target, characterSetName);
  }

}
