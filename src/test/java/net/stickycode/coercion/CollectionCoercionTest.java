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

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.AbstractList;
import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import static org.fest.assertions.Assertions.assertThat;


public class CollectionCoercionTest {
  @Rule
  public TestName testName = new TestName();

  AbstractList<Boolean> abstractCollection;

  ArrayList<Boolean> arrayList;

  @Test
  public void noCoercionForCollection() {
    assertThat(coercion(new ArrayList<Coercion<?>>()).isApplicableTo(coercionTarget(Boolean.class))).isFalse();
  }

  @Test
  public void applicable() {
    assertThat(coercion(booleanCoercion()).isApplicableTo(coercionTarget(Boolean.class))).isFalse();
  }

  @Test(expected=CollectionCoercionDoesNotHaveAnAppriateMappingException.class)
  public void abstractCollection() {
    coercion(new ArrayList<Coercion<?>>()).coerce(componentCoercionType(), "");
  }

  @Test
  public void arrayList() {
    coercion(booleanCoercion()).coerce(componentCoercionType(), "");
  }

  private ArrayList<Coercion<?>> booleanCoercion() {
    ArrayList<Coercion<?>> componentCoercions = new ArrayList<Coercion<?>>();
    componentCoercions.add(new ValueOfMethodCoercion());
    return componentCoercions;
  }

  private CoercionTarget coercionTarget(Class<?> type) {
    return new CoercionType(type);
  }

  private CollectionCoercion coercion(ArrayList<Coercion<?>> componentCoercions) {
    return new CollectionCoercion(componentCoercions);
  }

  private CoercionTarget componentCoercionType() {
    String name = testName.getMethodName();
    Field field = getField(name);
    return new CoercionType(field.getType(), (ParameterizedType) field.getGenericType());
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
