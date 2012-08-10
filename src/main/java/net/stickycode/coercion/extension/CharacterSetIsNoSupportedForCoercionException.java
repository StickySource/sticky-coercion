package net.stickycode.coercion.extension;

import java.util.Set;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class CharacterSetIsNoSupportedForCoercionException
    extends PermanentException {

  public CharacterSetIsNoSupportedForCoercionException(CoercionTarget target, String characterSetName, Set<String> names) {
    super("Failed to coerce '' as character set '' is not suppoerted, try one of ''", target, characterSetName, names);
  }

}
