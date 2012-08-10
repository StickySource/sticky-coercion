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
package net.stickycode.coercion.plural;

import static org.fest.assertions.Assertions.assertThat;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.coercion.Coercions;
import net.stickycode.coercion.plural.ArrayCoercion;
import net.stickycode.coercion.target.CoercionTargets;

import org.junit.Test;

public class ArrayCoercionTest {

  public static class Blah {
    private final String value;

    public Blah(String value) {
      super();
      this.value = value;
    }

    @Override
    public int hashCode() {
      return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      return value.equals(((Blah)obj).value);
    }
    
  }
  
  @Test
  public void stringConstructor() {
    ArrayCoercion coercion = coercion();
    CoercionTarget coercionTarget = coercionTarget(Blah[].class);
    assertThat(coercion.isApplicableTo(coercionTarget)).isTrue();
    assertThat((Blah[]) coercion.coerce(coercionTarget, "a")).containsOnly(new Blah("a"));
    assertThat((Blah[]) coercion.coerce(coercionTarget, "a,b,c")).containsOnly(new Blah("a"), new Blah("b"), new Blah( "c"));
    assertThat((Blah[]) coercion.coerce(coercionTarget, "abc,bde,ceg")).containsOnly(new Blah("abc"), new Blah("bde"), new Blah( "ceg"));
  }
  @Test
  public void string() {
    ArrayCoercion coercion = coercion();
    CoercionTarget coercionTarget = coercionTarget(String[].class);
    assertThat(coercion.isApplicableTo(coercionTarget)).isTrue();
    assertThat((String[]) coercion.coerce(coercionTarget, "a")).containsOnly("a");
    assertThat((String[]) coercion.coerce(coercionTarget, "a,b,c")).containsOnly("a", "b", "c");
    assertThat((String[]) coercion.coerce(coercionTarget, "abc,cba,k")).containsOnly("abc", "cba", "k");
  }

  @Test
  public void booleans() {
    ArrayCoercion coercion = coercion();
    CoercionTarget coercionTarget = coercionTarget(Boolean[].class);
    assertThat(coercion.isApplicableTo(coercionTarget)).isTrue();
    assertThat((Boolean[]) coercion.coerce(coercionTarget, "true")).containsOnly(true);
    assertThat((Boolean[]) coercion.coerce(coercionTarget, "true,false,true"))
              .containsOnly(true, false, true);
    assertThat((Boolean[]) coercion.coerce(coercionTarget, "true,true,false"))
              .containsOnly(true, true, false);
  }

  @Test
  public void bytes() {
    ArrayCoercion coercion = coercion();
    CoercionTarget coercionTarget = coercionTarget(byte[].class);
    assertThat(coercion.isApplicableTo(coercionTarget)).isTrue();
    assertThat((byte[]) coercion.coerce(coercionTarget, "45")).containsOnly((byte)45);
    assertThat((byte[]) coercion.coerce(coercionTarget, "45,127,-127")).containsOnly((byte)45, (byte)127, (byte)-127);
  }

  @Test
  public void integers() {
    ArrayCoercion coercion = coercion();
    CoercionTarget coercionTarget = coercionTarget(int[].class);
    assertThat(coercion.isApplicableTo(coercionTarget)).isTrue();
    assertThat((int[]) coercion.coerce(coercionTarget, "45")).containsOnly(45);
    assertThat((int[]) coercion.coerce(coercionTarget, "45,127,-127")).containsOnly(45, 127, -127);
  }
  
  

  private CoercionTarget coercionTarget(final Class<?> type) {
    return CoercionTargets.find(type);
  }

  private ArrayCoercion coercion() {
    return new ArrayCoercion(new Coercions());
  }
}
