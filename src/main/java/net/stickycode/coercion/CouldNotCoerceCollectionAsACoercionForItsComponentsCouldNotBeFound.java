package net.stickycode.coercion;

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
