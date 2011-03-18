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

import java.lang.reflect.ParameterizedType;

public abstract class AbstractCoercionType
    implements CoercionTarget {

  public abstract boolean isGenericType();

  public abstract ParameterizedType getGenericType();

  public abstract Class<?> getType();

  @Override
  public boolean isArray() {
    return getType().isArray();
  }

  @Override
  public Class<?> getComponentType() {
    if (isArray())
      return getType().getComponentType();

    if (isGenericType())
      return (Class<?>) getGenericType().getActualTypeArguments()[0];

    throw new RuntimeException();
  }

  public CoercionType getComponentCoercionType() {
    return new CoercionType(resolve(getComponentType()));
  }

  protected Class<?> resolve(Class<?> componentType) {
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
