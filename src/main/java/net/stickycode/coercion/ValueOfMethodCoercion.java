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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ValueOfMethodCoercion
    implements Coercion<Object> {

  private static final String VALUE_OF = "valueOf".intern();
  @Override
  public Object coerce(CoercionTarget type, String value) throws AbstractFailedToCoerceValueException {
    Method valueOfMethod = getValueOfMethod(type);
    if (valueOfMethod == null)
      throw new ValueOfMethodNotFoundForCoercionException(type.getType());

    return invokeMethod(type.getType(), valueOfMethod, value);
  }

  private Object invokeMethod(Class<?> type, Method valueOfMethod, String value) {
    try {
      return valueOfMethod.invoke(null, new Object[] {value});
    }
    catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    }
    catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean isApplicableTo(CoercionTarget type) {
    return getValueOfMethod(type) != null;
  }

  private Method getValueOfMethod(CoercionTarget type) {
    for (Method m : type.getType().getDeclaredMethods()) {
      if (m.getName() == VALUE_OF) {
        Class<?>[] parameterTypes = m.getParameterTypes();
        if (parameterTypes.length == 1)
          if (String.class.equals(parameterTypes[0]))
            if (type.getType().isAssignableFrom(m.getReturnType()))
                return m;
      }
    }

    return null;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }


}
