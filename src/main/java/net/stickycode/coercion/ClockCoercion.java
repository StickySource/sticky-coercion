package net.stickycode.coercion;


import java.time.Clock;
import java.time.ZonedDateTime;

import net.stickycode.stereotype.plugin.StickyExtension;

@StickyExtension
public class ClockCoercion
    implements Coercion<Clock> {

  @Override
  public boolean isApplicableTo(CoercionTarget target) {
    return Clock.class.isAssignableFrom(target.getType());
  }

  @Override
  public boolean hasDefaultValue() {
    return true;
  }

  @Override
  public Clock getDefaultValue(CoercionTarget target) {
    return Clock.systemDefaultZone();
  }

  @Override
  public Clock coerce(CoercionTarget type, String value) {
    if (value != null
      && !value.isEmpty()) {
      ZonedDateTime zonedDateTime = ZonedDateTime.parse(value);
      return Clock.fixed(zonedDateTime.toInstant(), zonedDateTime.getZone());
    }
    return getDefaultValue(type);
  }

}