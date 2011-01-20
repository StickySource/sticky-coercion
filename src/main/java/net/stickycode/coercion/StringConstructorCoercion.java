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

import java.lang.reflect.Constructor;


public class StringConstructorCoercion
    implements Coercion<Object> {

  @Override
  public Object coerce(CoercionTarget target, String value)
      throws AbstractFailedToCoerceValueException {
    Constructor<?> c = getStringConstructor(target);
    return construct(value, c);
  }

  private Object construct(String value, Constructor<?> c) {
    try {
      return c.newInstance(value);
    }
    catch (Exception e) {
      throw new EnumValueNotFoundException(e, value, c.getDeclaringClass());
    }
  }

  private Constructor<?> getStringConstructor(CoercionTarget target) {
    try {
      return target.getType().getConstructor(String.class);
    }
    catch (NoSuchMethodException e) {
      throw new StringConstructorNotFoundEvenThoughWeVerifiedItWasThere(e);
    }
  }

  @Override
  public boolean isApplicableTo(CoercionTarget target) {
    for (Constructor<?> c : target.getType().getConstructors()) {
      Class<?>[] parameterTypes = c.getParameterTypes();
      if (parameterTypes.length == 1)
        if (c.getParameterTypes()[0].equals(String.class))
          return true;
    }

    return false;
  }

}
