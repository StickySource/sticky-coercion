package net.stickycode.coercion.plural;

import net.stickycode.coercion.CoercionFinder;
import net.stickycode.coercion.CoercionNotFoundException;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class CouldNotCoerceCollectionAsACoercionForItsComponentsCouldNotBeFound
    extends PermanentException {

  public CouldNotCoerceCollectionAsACoercionForItsComponentsCouldNotBeFound(
      CoercionNotFoundException e,
      CoercionTarget target,
      CoercionFinder finder) {
    super(e, "Found {} but could not find a coercion for its components in {}", target, finder);
  }

}
