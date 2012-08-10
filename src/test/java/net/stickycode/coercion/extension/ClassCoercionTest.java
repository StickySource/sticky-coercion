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

import java.util.regex.Pattern;

import net.stickycode.coercion.AbstractCoercionTest;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.coercion.extension.ClassCoercion;
import net.stickycode.coercion.extension.ConfiguredClassIsNotOfTheCorrectTypeException;
import net.stickycode.coercion.extension.ConfiguredClassNotFoundException;
import net.stickycode.coercion.target.CoercionTargets;
import net.stickycode.reflector.Fields;

import org.junit.Test;

/**
 * {@link Pattern} does not implement equals so the standard {@link AbstractCoercionTest} cannot be used
 */
public class ClassCoercionTest {

  private static class Super {

  }

  private static class Concrete
      extends Super {

  }

  @SuppressWarnings("unused")
  private Class<Super> type;

  @Test
  public void isApplicable() {
    assertThat(coercion().isApplicableTo(coercionTarget()));
  }

  @Test
  public void superType() {
    assertThat(coercion().coerce(coercionTarget(), Super.class.getName()));
  }

  @Test
  public void concrete() {
    assertThat(coercion().coerce(coercionTarget(), Concrete.class.getName()));
  }

  @Test(expected = ConfiguredClassIsNotOfTheCorrectTypeException.class)
  public void incompatible() {
    assertThat(coercion().coerce(coercionTarget(), Pattern.class.getName()));
  }

  @Test(expected = ConfiguredClassNotFoundException.class)
  public void failure() {
    assertThat(coercion().coerce(coercionTarget(), "arandom-broken-name"));
  }

  private CoercionTarget coercionTarget() {
    return CoercionTargets.find(Fields.find(getClass(), "type"));
  }

  private ClassCoercion coercion() {
    return new ClassCoercion();
  }
}
