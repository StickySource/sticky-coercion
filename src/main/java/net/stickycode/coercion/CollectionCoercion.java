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

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CollectionCoercion
    implements Coercion<Collection<?>> {

  private CoercionFinder coercionFinder;

  public CollectionCoercion(CoercionFinder coercionFinder) {
    super();
    this.coercionFinder = coercionFinder;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public Collection<?> coerce(CoercionTarget type, String value)
      throws AbstractFailedToCoerceValueException {
    Coercion<?> componentCoercion = findComponentCoercion(type);

    String[] values = value.split(",");
    Collection list = createCollection(type, values.length);
    CoercionTarget componentTarget = type.getComponentCoercionType();
    for (int i = 0; i < values.length; i++) {
      list.add(componentCoercion.coerce(componentTarget, values[i]));
    }
    return list;
  }

  @SuppressWarnings("rawtypes")
  private Collection createCollection(CoercionTarget type, int length) {
    Class<?> collectionType = type.getType();
    if (collectionType.isInterface())
      return defaultInstanceForType(collectionType, length);

    if (Modifier.isAbstract(collectionType.getModifiers()))
      throw new CollectionCoercionDoesNotHaveAnAppriateMappingException(type.getType());

    return newInstance(collectionType);
  }

  @SuppressWarnings("rawtypes")
  private Collection defaultInstanceForType(Class<?> collectionType, int length) {
    if (List.class.isAssignableFrom(collectionType))
      return new ArrayList();

    if (Set.class.isAssignableFrom(collectionType))
      return new HashSet();

    if (Queue.class.isAssignableFrom(collectionType))
      return new ConcurrentLinkedQueue();

    if (Collection.class.isAssignableFrom(collectionType))
      return new ArrayList(length);

    throw new CollectionCoercionDoesNotHaveAnAppriateMappingException(collectionType);
  }

  @SuppressWarnings("rawtypes")
  private Collection newInstance(Class<?> collectionType) {
    try {
      return (Collection) collectionType.newInstance();
    }
    catch (InstantiationException e) {
      throw new RuntimeException(e);
    }
    catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean isApplicableTo(CoercionTarget target) {
    if (!Collection.class.isAssignableFrom(target.getType()))
      return false;

    return findComponentCoercion(target) != null;
  }

  private Coercion<?> findComponentCoercion(CoercionTarget target) {
    return coercionFinder.find(target.getComponentCoercionType());
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

}
