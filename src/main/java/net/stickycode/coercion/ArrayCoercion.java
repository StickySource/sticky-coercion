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

public class ArrayCoercion
    extends AbstractNoDefaultCoercion<Object>{

  private CoercionFinder finder;

  public ArrayCoercion(CoercionFinder finder) {
    super();
    this.finder = finder;
  }

  @Override
  public Object coerce(CoercionTarget target, String value) {
    
    CoercionTarget targetComponent = target.getComponentCoercionTypes()[0];
    Coercion<?> componentCoercion = finder.find(targetComponent);

    String[] values = value.split(",");
    Object array = Array.newInstance(targetComponent.getType(), values.length);
    for (int i = 0; i < values.length; i++) {
      Array.set(array, i, componentCoercion.coerce(targetComponent, values[i]));
    }
    return array;
  }

  @Override
  public boolean isApplicableTo(CoercionTarget target) {
    if (!target.isArray())
      return false;
    
    try {
      finder.find(target.getComponentCoercionTypes()[0]);
      return true;
    }
    catch (CoercionNotFoundException e) {
      throw new CouldNotCoerceArrayAsACoercionForItsComponentsCouldNotBeFound(e, target, finder);
    }
  }

}
