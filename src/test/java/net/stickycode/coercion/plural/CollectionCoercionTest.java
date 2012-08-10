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

import static org.fest.assertions.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;

import net.stickycode.coercion.Coercion;
import net.stickycode.coercion.CoercionFinder;
import net.stickycode.coercion.CoercionNotFoundException;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.coercion.ValueOfMethodCoercion;
import net.stickycode.coercion.plural.CollectionCoercion;
import net.stickycode.coercion.target.CoercionTargets;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class CollectionCoercionTest {

  @Rule
  public TestName testName = new TestName();

  AbstractList<Boolean> abstractCollection;

  ArrayList<Boolean> arrayList;

  @Test
  public void noCoercionForCollection() {
    assertThat(coercion(null).isApplicableTo(coercionTarget(Boolean.class))).isFalse();
  }

  @Test
  public void applicable() {
    assertThat(coercion(booleanCoercion()).isApplicableTo(coercionTarget(Boolean.class))).isFalse();
  }

  @Test(expected = CoercionNotFoundException.class)
  public void abstractCollection() {
    coercion(null).coerce(componentCoercionType(), "");
  }

  @Test
  public void arrayList() {
    coercion(booleanCoercion()).coerce(componentCoercionType(), "");
  }

  private Coercion<?> booleanCoercion() {
    return new ValueOfMethodCoercion();
  }

  private CoercionTarget coercionTarget(Class<?> type) {
    return CoercionTargets.find(type);
  }

  private CollectionCoercion coercion(final Coercion<?> componentCoercion) {
    return new CollectionCoercion(new CoercionFinder() {
      @SuppressWarnings({ "rawtypes", "unchecked" })
      @Override
      public Coercion<?> find(CoercionTarget target) throws CoercionNotFoundException {
        if (componentCoercion == null)
          throw new CoercionNotFoundException(target, Collections.<Coercion>emptySet());

        return componentCoercion;
      }
    });
  }

  private CoercionTarget componentCoercionType() {
    String name = testName.getMethodName();
    Field field = getField(name);
    return CoercionTargets.find(field);
  }

  private Field getField(String name) {
    try {
      return getClass().getDeclaredField(name);
    }
    catch (SecurityException e) {
      throw new RuntimeException(e);
    }
    catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }

}
