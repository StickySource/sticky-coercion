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

public class ParseMethodCoercion
    extends AbstractNoDefaultCoercion<Object> {

  private static final String PARSE = "parse".intern();

  @Override
  public Object coerce(CoercionTarget type, String value) {
    Method parseMethod = getParseMethod(type);
    if (parseMethod == null)
      throw new ParseMethodNotFoundForCoercionException(type.getType());

    return invokeMethod(type.getType(), parseMethod, value);
  }

  private Object invokeMethod(Class<?> type, Method parseMethod, String value) {
    try {
      return parseMethod.invoke(null, new Object[] { value });
    }
    catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    }
    catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    catch (InvocationTargetException e) {
      throw new FailedToCoerceUsingParseMethodException(e, type, value);
    }
  }

  @Override
  public boolean isApplicableTo(CoercionTarget type) {
    return getParseMethod(type) != null;
  }

  private Method getParseMethod(CoercionTarget type) {
    if (type.isPrimitive())
      return getParseMethod(type.boxedType());

    return getParseMethod(type.getType());
  }

  private Method getParseMethod(Class<?> type2) {
    for (Method m : type2.getDeclaredMethods()) {
      if (m.getName() == PARSE) {
        Class<?>[] parameterTypes = m.getParameterTypes();
        if (parameterTypes.length == 1)
          if (CharSequence.class.equals(parameterTypes[0]))
            if (type2.isAssignableFrom(m.getReturnType()))
              return m;
      }
    }

    return null;
  }

}
