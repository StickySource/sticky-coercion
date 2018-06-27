package net.stickycode.coercion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.StrictAssertions.assertThat;

import java.time.Clock;
import java.time.Instant;

import org.junit.Test;

import net.stickycode.coercion.target.CoercionTargets;

public class ClockCoercionTest {

  private Clock coercedClock;

  private ClockCoercion coercion = new ClockCoercion();

  @Test
  public void testHasDefaultValue() {
    assertThat(coercion.hasDefaultValue()).isTrue();
  }

  @Test
  public void testGetDefaultValue() {
    assertThat(coercion
      .getDefaultValue(null)
      .instant())
        .isLessThanOrEqualTo(Instant.now());
  }

  @Test
  public void testApplicableTo() {
    assertThat(coercion.isApplicableTo(CoercionTargets.find(ClockCoercion.class))).isFalse();
    assertThat(coercion.isApplicableTo(CoercionTargets.find(Clock.class))).isTrue();
  }

  @Test
  public void testCoerce() {
    assertThat(coercion
      .coerce(CoercionTargets.find(Clock.class), null)
      .instant())
        .isLessThanOrEqualTo(Instant.now());
    coerceParsed("2018-03-31T00:00:00.000Z", "2018-03-31T00:00:00.000Z");
    coerceParsed("2018-03-31T00:00:00+12:00[Pacific/Auckland]", "2018-03-30T11:00:00.000Z");
  }

  private void coerceParsed(String parseValue, String parseInstant) {
    assertThat(coercion
      .coerce(CoercionTargets.find(Clock.class), parseValue).instant())
        .isEqualTo(Instant.parse(parseInstant));
  }

}