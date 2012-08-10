package net.stickycode.coercion.extension;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class ConfiguredClassNotFoundException
    extends PermanentException {

  public ConfiguredClassNotFoundException(ClassNotFoundException e, String value) {
    super(e, "Class not found looking for ''", value);
  }

}
