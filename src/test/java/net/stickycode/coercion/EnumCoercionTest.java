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
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.junit.Test;

public class EnumCoercionTest
    extends AbstractCoercionTest {

  Set<ExampleEnum> set;
  List<ExampleEnum> list;
  Queue<ExampleEnum> queue;
  Collection<ExampleEnum> collection;

  private enum ExampleEnum {
    One,
    Two,
    Three
  }

  @Test(expected = EnumValueNotFoundException.class)
  public void invalid() {
    coercion().coerce(coercionType(ExampleEnum.class), "Other");
  }

  @Test(expected = EnumValueNotFoundException.class)
  public void empty() {
    coercion().coerce(coercionType(ExampleEnum.class), "");
  }

  @Test(expected = EnumValueOfMethodNotFoundEvenThoughWeVerifiedItWasThere.class)
  public void noValueOfMethod() {
    coercion().coerce(coercionType(URL.class), "ug");
  }

  protected Coercion<?> coercion() {
    return new EnumCoercion();
  }

  @Override
  protected String firstValue() {
    return "One";
  }

  @Override
  protected String secondValue() {
    return "Two";
  }

  @Override
  protected String thirdValue() {
    return "Three";
  }

  @Override
  protected Object firstResult() {
    return ExampleEnum.One;
  }

  @Override
  protected Object secondResult() {
    return ExampleEnum.Two;
  }

  @Override
  protected Object thirdResult() {
    return ExampleEnum.Three;
  }

  @Override
  protected Class<?> getInapplicableType() {
    return Boolean.class;
  }

  @Override
  protected Class<?> getApplicableType() {
    return ExampleEnum.class;
  }

  @Override
  protected Class<?> getApplicableArrayType() {
    return ExampleEnum[].class;
  }

}
