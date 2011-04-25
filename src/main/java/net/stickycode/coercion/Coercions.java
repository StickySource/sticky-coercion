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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import net.stickycode.stereotype.StickyComponent;

@StickyComponent
public class Coercions
    implements CoercionFinder {

  /*
   * XXX If you can figure out how to get guice to multibind to Set<Coercion<?>> then
   * let me know and I'll put the <?> back in.
   */

  @SuppressWarnings("rawtypes")
  private List<Coercion> coercions = new ArrayList<Coercion>();

  @Inject
  @SuppressWarnings("rawtypes")
  private Set<Coercion> extensions;

  public Coercions() {
    coercions.add(new StringCoercion());
    coercions.add(new CollectionCoercion(this));
    coercions.add(new ValueOfMethodCoercion());
    coercions.add(new StringConstructorCoercion());
  }

  @Override
  public Coercion<?> find(CoercionTarget target)
      throws CoercionNotFoundException {

    if (extensions != null) {
      for (Coercion<?> c : extensions)
        if (c.isApplicableTo(target))
          return c;
    }

    for (Coercion<?> c : coercions) {
      if (c.isApplicableTo(target))
        return c;
    }

    throw new CoercionNotFoundException(target, getList());
  }

  @SuppressWarnings("rawtypes")
  public List<Coercion> getList() {
    ArrayList<Coercion> all = new ArrayList<Coercion>();
    if (extensions != null)
      all.addAll(extensions);

    all.addAll(coercions);
    return all;
  }

}
