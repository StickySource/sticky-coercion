package net.stickycode.coercion.extension;

import net.stickycode.exception.PermanentException;


@SuppressWarnings("serial")
public class ConfiguredClassIsNotOfTheCorrectTypeException
    extends PermanentException {

  public ConfiguredClassIsNotOfTheCorrectTypeException(Class<?> expected, Class<?> loaded) {
    super("Expected '' but got ''", expected, loaded);
  }

}
