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

import static org.assertj.core.api.StrictAssertions.assertThat;

import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

import net.stickycode.coercion.target.CoercionTargets;

public class ParseMethodCoercionTest {

  @Test
  public void isApplicable() {
    assertThat(coercion().isApplicableTo(coercionTarget(Integer.class))).isFalse(); // sanity check

    assertThat(coercion().isApplicableTo(coercionTarget(Duration.class))).isTrue();
    assertThat(coercion().isApplicableTo(coercionTarget(Instant.class))).isTrue();
    assertThat(coercion().isApplicableTo(coercionTarget(LocalDate.class))).isTrue();
    assertThat(coercion().isApplicableTo(coercionTarget(Period.class))).isTrue();
    assertThat(coercion().isApplicableTo(coercionTarget(Year.class))).isTrue();
    assertThat(coercion().isApplicableTo(coercionTarget(YearMonth.class))).isTrue();
    assertThat(coercion().isApplicableTo(coercionTarget(ZonedDateTime.class))).isTrue();
  }

  @Test
  public void coerce() {
    assertThat(coercion().coerce(coercionTarget(Duration.class), "PT10s")).isEqualTo(Duration.ofSeconds(10));
    assertThat(coercion().coerce(coercionTarget(Instant.class), "1970-01-01T00:00:01Z")).isEqualTo(Instant.ofEpochSecond(1));
    assertThat(coercion().coerce(coercionTarget(LocalDate.class), "2011-04-11")).isEqualTo(LocalDate.of(2011, 04, 11));
    assertThat(coercion().coerce(coercionTarget(Period.class), "P10Y")).isEqualTo(Period.ofYears(10));
    assertThat(coercion().coerce(coercionTarget(Year.class), "1977")).isEqualTo(Year.of(1977));
    assertThat(coercion().coerce(coercionTarget(YearMonth.class), "1977-04")).isEqualTo(YearMonth.of(1977, 04));
    assertThat(coercion()
      .coerce(coercionTarget(ZonedDateTime.class), "2007-12-03T10:15:30+13:00[Pacific/Auckland]"))
        .isEqualTo(ZonedDateTime.of(2007, 12, 03, 10, 15, 30, 0, ZoneId.of("Pacific/Auckland")));
  }

  @Test(expected = FailedToCoerceUsingParseMethodException.class)
  public void invalidInteger() {
    coercion().coerce(coercionTarget(Year.class), "other");
  }

  @Test(expected = ParseMethodNotFoundForCoercionException.class)
  public void noValueOfMethod() {
    coercion().coerce(coercionTarget(StringConstructorCoercion.class), "ug");
  }

  private Coercion<?> coercion() {
    return new ParseMethodCoercion();
  }

  private CoercionTarget coercionTarget(final Class<?> type) {
    return CoercionTargets.find(type);
  }

}
