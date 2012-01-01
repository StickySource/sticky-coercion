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

import java.util.regex.Pattern;

import net.stickycode.coercion.target.CoercionTargets;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * {@link Pattern} does not implement equals so the standard {@link AbstractCoercionTest} cannot be used
 */
public class PatternCoercionTest {

  @Test
  public void isApplicable() {
    assertThat(coercion().isApplicableTo(coercionTarget()));
  }

  @Test
  public void patterns() {
    assertThat(coercion().coerce(null, ""));
    assertThat(coercion().coerce(null, "^$"));
    assertThat(coercion().coerce(null, "^a$"));
    assertThat(coercion().coerce(null, ".*"));
  }

  @Test(expected = PatternCouldNotBeCoercedException.class)
  public void failure() {
    assertThat(coercion().coerce(null, "("));
  }

  private CoercionTarget coercionTarget() {
    return CoercionTargets.find(Pattern.class);
  }

  private PatternCoercion coercion() {
    return new PatternCoercion();
  }
}
