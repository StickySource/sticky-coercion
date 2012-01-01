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

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class StringCoercionTest
    extends AbstractCoercionTest {

  List<String> list;

  Set<String> set;

  Queue<String> queue;

  Collection<String> collection;

  @Override
  protected StringCoercion coercion() {
    return new StringCoercion();
  }

  protected Class<Boolean> getInapplicableType() {
    return Boolean.class;
  }

  protected Class<?> getApplicableType() {
    return String.class;
  }

  @Override
  protected Class<?> getApplicableArrayType() {
    return String[].class;
  }
  
  @Test
  public void coerce() {
    assertThat(coercion().coerce(null, "blah")).isEqualTo("blah");
    assertThat(coercion().coerce(null, "something")).isEqualTo("something");
    assertThat(coercion().coerce(null, "")).isEqualTo("");
  }

  @Override
  protected String firstResult() {
    return "a";
  }

  protected String secondResult() {
    return "kck";
  }

  @Override
  protected String thirdResult() {
    return "sfxs";
  }

  @Override
  protected String thirdValue() {
    return "sfxs";
  }

  @Override
  protected String secondValue() {
    return "kck";
  }

  @Override
  protected String firstValue() {
    return "a";
  }

}
