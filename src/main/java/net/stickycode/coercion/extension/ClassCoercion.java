package net.stickycode.coercion.extension;

import net.stickycode.coercion.AbstractNoDefaultCoercion;
import net.stickycode.coercion.CoercionTarget;

public class ClassCoercion
    extends AbstractNoDefaultCoercion<Class<?>> {

  @Override
  public Class<?> coerce(CoercionTarget type, String value) {
    try {
      Class<?> loaded = type.getOwner().getClassLoader().loadClass(value);
      Class<?> expected = type.getComponentCoercionTypes()[0].getType();
      if (expected.isAssignableFrom(loaded))
        return loaded;
      
      throw new ConfiguredClassIsNotOfTheCorrectTypeException(expected, loaded);
    }
    catch (ClassNotFoundException e) {
      throw new ConfiguredClassNotFoundException(e, value);
    }
  }

  @Override
  public boolean isApplicableTo(CoercionTarget target) {
    return Class.class.isAssignableFrom(target.getType());
  }
}
