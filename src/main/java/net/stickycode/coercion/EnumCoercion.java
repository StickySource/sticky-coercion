/**
 * Copyright (c) 2010 RedEngine Ltd, http://www.redengine.co.nz. All rights reserved.
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

import java.lang.reflect.Method;

public class EnumCoercion
    implements Coercion<Object> {

  @Override
  public Object coerce(CoercionTarget target, String value) {
    Method factoryMethod = getValueOfMethod(target);
    return getEnumValue(value, factoryMethod);
  }

  private Object getEnumValue(String value, Method factoryMethod) {
    try {
      return factoryMethod.invoke(null, new Object[] { value });
    }
    catch (Exception e) {
      throw new EnumValueNotFoundException(e, value, factoryMethod.getReturnType());
    }
  }

  private Method getValueOfMethod(CoercionTarget target) {
    try {
      return target.getType().getMethod("valueOf", new Class[] { String.class });
    }
    catch (NoSuchMethodException e) {
      throw new EnumValueOfMethodNotFoundEvenThoughWeVerifiedItWasThere(e);
    }
  }

  @Override
  public boolean isApplicableTo(CoercionTarget target) {
    return target.getType().isEnum();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

}
