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

import java.net.URL;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;


public class StringConstructorCoercionTest {

  @Test
  public void isApplicable() {
    assertThat(coercion().isApplicableTo(coercionTarget(String.class))).isTrue();
    assertThat(coercion().isApplicableTo(coercionTarget(Boolean.class))).isTrue();
    assertThat(coercion().isApplicableTo(coercionTarget(URL.class))).isTrue();
  }

  @Test
  public void coerce() {
    assertThat(coercion().coerce(coercionTarget(String.class), "blah")).isEqualTo("blah");
    assertThat(coercion().coerce(coercionTarget(Boolean.class), "true")).isEqualTo(Boolean.TRUE);
    assertThat(coercion().coerce(coercionTarget(Boolean.class), "false")).isEqualTo(Boolean.FALSE);
    assertThat(coercion().coerce(coercionTarget(Integer.class), "10")).isEqualTo(10);
    assertThat(coercion().coerce(coercionTarget(Float.class), "-10.5")).isEqualTo(-10.5f);
  }

  @Test(expected=StringConstructorNotFoundEvenThoughWeVerifiedItWasThere.class)
  public void noStringConstructor() {
    coercion().coerce(coercionTarget(StringConstructorCoercion.class), "ug");
  }

  private StringConstructorCoercion coercion() {
    return new StringConstructorCoercion();
  }

  private CoercionTarget coercionTarget(final Class<?> type) {
    return new CoercionTarget() {
      @Override
      public Class<?> getType() {
        return type;
      }
    };
  }

}
