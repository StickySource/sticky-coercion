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
package net.stickycode.coercion.plural;

import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;

import net.stickycode.coercion.AbstractNoDefaultCoercion;
import net.stickycode.coercion.Coercion;
import net.stickycode.coercion.CoercionFinder;
import net.stickycode.coercion.CoercionNotFoundException;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.coercion.StringSpliterable;

public class CollectionCoercion
    extends AbstractNoDefaultCoercion<Collection<?>> {

  private CoercionFinder coercionFinder;

  public CollectionCoercion(CoercionFinder coercionFinder) {
    super();
    this.coercionFinder = coercionFinder;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public Collection<?> coerce(CoercionTarget target, String value) {
    CoercionTarget componentTarget = target.getComponentCoercionTypes()[0];
    Coercion<?> componentCoercion = coercionFinder.find(componentTarget);

    String[] values = new StringSpliterable(value).asArray() ;
    Collection list = createCollection(target, values.length);
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
      throw new CollectionCoercionDoesNotHaveAnAppropriateMappingException(type.getType());

    return newInstance(collectionType);
  }

  @SuppressWarnings("rawtypes")
  private Collection defaultInstanceForType(Class<?> collectionType, int length) {
    if (List.class.isAssignableFrom(collectionType))
      return new ArrayList();

    if (NavigableSet.class.isAssignableFrom(collectionType))
      return new TreeSet();
    
    if (SortedSet.class.isAssignableFrom(collectionType))
      return new TreeSet();
    
    if (Set.class.isAssignableFrom(collectionType))
      return new HashSet();

    if (BlockingDeque.class.isAssignableFrom(collectionType))
      return new LinkedBlockingDeque();
    
    if (Deque.class.isAssignableFrom(collectionType))
      return new ArrayDeque();
    
    if (Queue.class.isAssignableFrom(collectionType))
      return new ConcurrentLinkedQueue();

    if (Collection.class.isAssignableFrom(collectionType))
      return new ArrayList(length);

    throw new CollectionCoercionDoesNotHaveAnAppropriateMappingException(collectionType);
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
    
    try {
      coercionFinder.find(target.getComponentCoercionTypes()[0]);
      return true;
    }
    catch (CoercionNotFoundException e) {
      throw new CouldNotCoerceCollectionAsACoercionForItsComponentsCouldNotBeFound(e, target, coercionFinder);
    }
  }

}
