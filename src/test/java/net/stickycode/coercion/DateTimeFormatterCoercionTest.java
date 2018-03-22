package net.stickycode.coercion;

import static org.assertj.core.api.StrictAssertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import net.stickycode.coercion.target.CoercionTargets;

public class DateTimeFormatterCoercionTest {

  @Test
  public void isApplicable() {
    assertThat(coercion().isApplicableTo(coercionTarget())).isTrue();
  }

  @Test
  public void formats() {
    LocalDateTime time = LocalDateTime.of(1980, 03, 20, 10, 33);
    assertThat(coerce("yyyy MM").format(time)).isEqualTo("1980 03");
    assertThat(coerce("'1970 01'").format(time)).isEqualTo("1970 01");
    assertThat(coerce("yyyy/MM/dd hh:mm").format(time)).isEqualTo("1980/03/20 10:33");
  }

  private DateTimeFormatter coerce(String value) {
    return coercion().coerce(coercionTarget(), value);
  }

  @Test(expected = IllegalArgumentException.class)
  public void failure() {
    assertThat(coercion().coerce(null, "l"));
  }

  private CoercionTarget coercionTarget() {
    return CoercionTargets.find(DateTimeFormatter.class);
  }

  private DateTimeFormatterCoercion coercion() {
    return new DateTimeFormatterCoercion();
  }

}
