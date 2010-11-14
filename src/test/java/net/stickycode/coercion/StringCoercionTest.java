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

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;


public class StringCoercionTest {

  @Test
  public void isApplicable() {
    assertThat(stringCoercion().isApplicableTo(coercionTarget(String.class))).isTrue();
    assertThat(stringCoercion().isApplicableTo(coercionTarget(Boolean.class))).isFalse();
  }

  @Test
  public void coerce() {
    assertThat(stringCoercion().coerce(null, "blah")).isEqualTo("blah");
    assertThat(stringCoercion().coerce(null, "something")).isEqualTo("something");
    assertThat(stringCoercion().coerce(null, "")).isEqualTo("");
  }

  private StringCoercion stringCoercion() {
    return new StringCoercion();
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
