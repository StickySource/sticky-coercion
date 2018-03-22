package net.stickycode.coercion;

import java.time.format.DateTimeFormatter;

import net.stickycode.stereotype.plugin.StickyExtension;

@StickyExtension
public class DateTimeFormatterCoercion
    extends AbstractNoDefaultCoercion<DateTimeFormatter> {

  @Override
  public DateTimeFormatter coerce(CoercionTarget type, String value) {
    return DateTimeFormatter.ofPattern(value);
  }

  @Override
  public boolean isApplicableTo(CoercionTarget target) {
    return DateTimeFormatter.class.isAssignableFrom(target.getType());
  }

}
