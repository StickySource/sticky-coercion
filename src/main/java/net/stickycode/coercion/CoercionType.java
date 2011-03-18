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

import java.lang.reflect.ParameterizedType;

final class CoercionType
    extends AbstractCoercionType {

  private final Class<?> type;
  private final ParameterizedType genericType;

  CoercionType(Class<?> type) {
    this.type = type;
    this.genericType = null;
  }

  public CoercionType(Class<?> type, ParameterizedType genericType) {
    this.type = type;
    this.genericType = genericType;
  }

  @Override
  public Class<?> getType() {
    return type;
  }

  @Override
  public ParameterizedType getGenericType() {
    return genericType;
  }

  @Override
  public boolean isGenericType() {
    return getGenericType() != null;
  }

}
