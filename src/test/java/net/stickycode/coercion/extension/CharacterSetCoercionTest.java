/**
 * Copyright (c) 2012 RedEngine Ltd, http://www.redengine.co.nz. All rights reserved.
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
package net.stickycode.coercion.extension;

import static org.fest.assertions.Assertions.assertThat;

import java.nio.charset.Charset;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.coercion.target.CoercionTargets;

import org.junit.Test;

public class CharacterSetCoercionTest {

  @Test
  public void isApplicable() {
    assertThat(coercion().isApplicableTo(coercionTarget()));
  }

  @Test
  public void ascii() {
    assertThat(coercion().coerce(coercionTarget(), "ascii"));
  }

  @Test
  public void utf8() {
    assertThat(coercion().coerce(coercionTarget(), "UTF-8"));
  }

  @Test(expected = CharacterSetIsNoSupportedForCoercionException.class)
  public void notfound() {
    assertThat(coercion().coerce(coercionTarget(), "XYZ-17"));
  }

  private CoercionTarget coercionTarget() {
    return CoercionTargets.find(Charset.class);
  }

  private CharacterSetCoercion coercion() {
    return new CharacterSetCoercion();
  }
}
