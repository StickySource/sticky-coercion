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

import java.net.URL;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;


public class ValueOfMethodCoercionTest {

  @Test
  public void isApplicable() {
    assertThat(coercion().isApplicableTo(coercionTarget(String.class))).isFalse();
    assertThat(coercion().isApplicableTo(coercionTarget(Integer.class))).isTrue();
    assertThat(coercion().isApplicableTo(coercionTarget(Float.class))).isTrue();
    assertThat(coercion().isApplicableTo(coercionTarget(Short.class))).isTrue();
    assertThat(coercion().isApplicableTo(coercionTarget(Double.class))).isTrue();
    assertThat(coercion().isApplicableTo(coercionTarget(Byte.class))).isTrue();
    assertThat(coercion().isApplicableTo(coercionTarget(Boolean.class))).isTrue();
    assertThat(coercion().isApplicableTo(coercionTarget(URL.class))).isFalse();
  }

  @Test
  public void coerce() {
    assertThat(coercion().coerce(coercionTarget(Boolean.class), "true")).isEqualTo(Boolean.TRUE);
    assertThat(coercion().coerce(coercionTarget(Boolean.class), "false")).isEqualTo(Boolean.FALSE);
    assertThat(coercion().coerce(coercionTarget(Integer.class), "10")).isEqualTo(10);
    assertThat(coercion().coerce(coercionTarget(Float.class), "-10.5")).isEqualTo(-10.5f);
  }

  @Test(expected=ValueOfMethodNotFoundForCoercionException.class)
  public void noValueOfMethod() {
    coercion().coerce(coercionTarget(StringConstructorCoercion.class), "ug");
  }

  private Coercion<?> coercion() {
    return new ValueOfMethodCoercion();
  }

  private CoercionTarget coercionTarget(final Class<?> type) {
    return new CoercionType(type);
  }

}
