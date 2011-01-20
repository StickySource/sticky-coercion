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


public class EnumCoercionTest {

  private enum ExampleEnum {
    One,
    Two
  }

  @Test
  public void isApplicable() {
    assertThat(coercion().isApplicableTo(coercionTarget(ExampleEnum.class))).isTrue();
    assertThat(coercion().isApplicableTo(coercionTarget(Boolean.class))).isFalse();
  }

  @Test
  public void coerce() {
    assertThat(coercion().coerce(coercionTarget(ExampleEnum.class), "One")).isEqualTo(ExampleEnum.One);
    assertThat(coercion().coerce(coercionTarget(ExampleEnum.class), "Two")).isEqualTo(ExampleEnum.Two);
  }

  @Test(expected=EnumValueNotFoundException.class)
  public void invalid() {
    coercion().coerce(coercionTarget(ExampleEnum.class), "Other");
  }

  @Test(expected=EnumValueNotFoundException.class)
  public void empty() {
    coercion().coerce(coercionTarget(ExampleEnum.class), "");
  }

  @Test(expected=EnumValueOfMethodNotFoundEvenThoughWeVerifiedItWasThere.class)
  public void noValueOfMethod() {
    coercion().coerce(coercionTarget(URL.class), "ug");
  }

  private EnumCoercion coercion() {
    return new EnumCoercion();
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
