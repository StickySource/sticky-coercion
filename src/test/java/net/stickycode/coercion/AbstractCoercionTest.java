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

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.Collection;

import net.stickycode.coercion.plural.ArrayCoercion;
import net.stickycode.coercion.plural.CollectionCoercion;
import net.stickycode.coercion.target.CoercionTargets;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public abstract class AbstractCoercionTest {

  @Rule
  public TestName testName = new TestName();

  protected abstract Coercion<?> coercion();

  protected abstract String firstValue();

  protected abstract String secondValue();

  protected abstract String thirdValue();

  private String twoValues() {
    return firstValue() + "," + secondValue();
  }

  private String threeValues() {
    return firstValue() + "," + secondValue() + "," + thirdValue();
  }


  protected abstract Object firstResult();

  protected abstract Object secondResult();

  protected abstract Object thirdResult();

  public AbstractCoercionTest() {
    super();
  }

  @Test
  public void isApplicable() {
    assertThat(coercion().isApplicableTo(coercionType(getApplicableType()))).isTrue();
    assertThat(coercion().isApplicableTo(coercionType(getInapplicableType()))).isFalse();
  }

  @Test
  public void coerce() {
    assertThat(firstResult()).isEqualTo(firstResult());
    assertThat(coercion().coerce(coercionType(getApplicableType()), firstValue())).isEqualTo(firstResult());
    assertThat(coercion().coerce(coercionType(getApplicableType()), secondValue())).isEqualTo(secondResult());
    assertThat(coercion().coerce(coercionType(getApplicableType()), thirdValue())).isEqualTo(thirdResult());
  }

  protected abstract Class<?> getInapplicableType();

  protected abstract Class<?> getApplicableType();

  @Test
  public void list() {
    assertThat(coerceCollection(firstValue())).containsOnly(firstResult());
    assertThat(coerceCollection(twoValues())).containsOnly(firstResult(), secondResult());
    assertThat(coerceCollection(threeValues())).containsOnly(firstResult(), secondResult(), thirdResult());
  }

  @Test
  public void set() {
    assertThat(coerceCollection(firstValue())).containsOnly(firstResult());
    assertThat(coerceCollection(twoValues())).containsOnly(firstResult(), secondResult());
    assertThat(coerceCollection(threeValues())).containsOnly(firstResult(), secondResult(), thirdResult());
  }

  @Test
  public void queue() {
    assertThat(coerceCollection(firstValue())).containsOnly(firstResult());
    assertThat(coerceCollection(twoValues())).containsOnly(firstResult(), secondResult());
    assertThat(coerceCollection(threeValues())).containsOnly(firstResult(), secondResult(), thirdResult());
  }

  @Test
  public void array() {
    assertThat(coerceArray(firstValue())).containsOnly(firstResult());
    assertThat(coerceArray(twoValues())).containsOnly(firstResult(), secondResult());
    assertThat(coerceArray(threeValues())).containsOnly(firstResult(), secondResult(), thirdResult());
  }

  @Test
  public void collection() {
    assertThat(coerceCollection(firstValue())).containsOnly(firstResult());
    assertThat(coerceCollection(twoValues())).containsOnly(firstResult(), secondResult());
    assertThat(coerceCollection(threeValues())).containsOnly(firstResult(), secondResult(), thirdResult());
  }

  protected CoercionTarget coercionType(final Class<?> type) {
    return CoercionTargets.find(type);
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

  @SuppressWarnings("unchecked")
  protected Collection<Object> coerceCollection(String value) {
    return (Collection<Object>) collectionCoercion().coerce(componentCoercionType(), value);
  }

  protected Object[] coerceArray(String value) {
    return (Object[]) arrayCoercion().coerce(coercionType(getApplicableArrayType()), value);
  }

  protected abstract Class<?> getApplicableArrayType();

  private CoercionTarget componentCoercionType() {
    String name = testName.getMethodName();
    Field field = getField(name);
    return CoercionTargets.find(field.getGenericType(), getClass(), field.getName());
  }

  private CollectionCoercion collectionCoercion() {
    return new CollectionCoercion(new CoercionFinder() {

      @SuppressWarnings("unchecked")
      @Override
      public Coercion<?> find(CoercionTarget target) throws CoercionNotFoundException {
        return coercion();
      }
    });
  }

  private ArrayCoercion arrayCoercion() {
    return new ArrayCoercion(new Coercions());
  }

}
