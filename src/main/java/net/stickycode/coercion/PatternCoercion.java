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

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import net.stickycode.stereotype.component.StickyMapper;

@StickyMapper
public class PatternCoercion
    implements Coercion<Pattern> {

  @Override
  public Pattern coerce(CoercionTarget type, String value)
      throws PatternCouldNotBeCoercedException {
    try {
      return Pattern.compile(value);
    }
    catch (PatternSyntaxException e) {
      throw new PatternCouldNotBeCoercedException(e, value);
    }
  }

  @Override
  public boolean isApplicableTo(CoercionTarget type) {
    return type.getType().isAssignableFrom(Pattern.class);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

}
