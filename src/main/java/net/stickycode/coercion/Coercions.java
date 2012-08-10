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

import net.stickycode.coercion.plural.ArrayCoercion;
import net.stickycode.coercion.plural.CollectionCoercion;
import net.stickycode.stereotype.StickyComponent;

@StickyComponent
public class Coercions
    implements CoercionFinder {
  /**
   * These coercions always go first and cannot be overridden
   */
  private List<Coercion<?>> overridingCoercions = new ArrayList<Coercion<?>>();

  
  /*
   * XXX If you can figure out how to get guice to multibind to Set<Coercion<?>> then
   * let me know and I'll put the <?> back in.
   */
  @SuppressWarnings("rawtypes")
  @Inject
  private Set<Coercion> extensions;

  /**
   * General coercions that are likely to be overridable with more specific extensions.
   */
  private List<Coercion<?>> fallBackCoercions = new ArrayList<Coercion<?>>();

  public Coercions() {
    overridingCoercions.add(new StringCoercion());
    fallBackCoercions.add(new ArrayCoercion(this));
    fallBackCoercions.add(new CollectionCoercion(this));
    fallBackCoercions.add(new ValueOfMethodCoercion());
    fallBackCoercions.add(new StringConstructorCoercion());
  }

  @SuppressWarnings("unchecked")
  @Override
  public Coercion<?> find(CoercionTarget target)
      throws CoercionNotFoundException {

    for (Coercion<?> c : overridingCoercions) {
      if (c.isApplicableTo(target))
        return c;
    }

    if (extensions != null) {
      for (Coercion<?> c : extensions)
        if (c.isApplicableTo(target))
          return c;
    }

    for (Coercion<?> c : fallBackCoercions) {
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

    all.addAll(fallBackCoercions);
    return all;
  }

  @Override
  public String toString() {
    return "Coercions{fixed=" + overridingCoercions + ", extensions=" + extensions + ", fallbacks=" + fallBackCoercions + "}";
  }

}
