/**
 * Copyright (c) 2011 RedEngine Ltd, http://www.redengine.co.nz. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package net.stickycode.coercion;

import java.lang.reflect.Array;
import java.util.List;

public class ArrayCoercion
    implements Coercion<Object> {

  private final List<? extends Coercion<?>> componentCoercions;

  public ArrayCoercion(List<? extends Coercion<?>> componentCoercions) {
    super();
    this.componentCoercions = componentCoercions;
  }

  @Override
  public Object coerce(CoercionTarget type, String value)
      throws AbstractFailedToCoerceValueException {
    Coercion<?> componentCoercion = findComponentCoercion(type);

    String[] values = value.split(",");
    Object array = Array.newInstance(type.getType().getComponentType(), values.length);
    CoercionTarget componentTarget = componentTarget(type);
    for (int i = 0; i < values.length; i++) {
      Array.set(array, i, componentCoercion.coerce(componentTarget, values[i]));
    }
    return array;
  }

  @Override
  public boolean isApplicableTo(CoercionTarget target) {
    if (!target.getType().isArray())
      return false;

    return findComponentCoercion(target) != null;
  }

  private Coercion<?> findComponentCoercion(CoercionTarget target) {
    CoercionTarget t = componentTarget(target);
    for (Coercion<?> c : componentCoercions) {
      if (c.isApplicableTo(t))
        return c;
    }

    return null;
  }

  private CoercionTarget componentTarget(final CoercionTarget target) {
    final Class<?> componentType = resolve(target.getType().getComponentType());
    CoercionTarget t = new CoercionTarget() {

      @Override
      public Class<?> getType() {
        return componentType;
      }
    };
    return t;
  }

  private Class<?> resolve(Class<?> componentType) {
    if (!componentType.isPrimitive())
      return componentType;

    if (boolean.class.equals(componentType))
      return Boolean.class;

    if (int.class.equals(componentType))
      return Integer.class;

    if (float.class.equals(componentType))
      return Float.class;

    if (double.class.equals(componentType))
      return Double.class;

    if (byte.class.equals(componentType))
      return Byte.class;

    if (short.class.equals(componentType))
      return Short.class;

    throw new UnknownPrimitiveTypeException(componentType.getName());
  }

}
