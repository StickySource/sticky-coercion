package net.stickycode.coercion;

import java.time.format.DateTimeFormatter;

public class DateTimeFormatterCoercion
    implements Coercion<DateTimeFormatter> {

  @Override
  public DateTimeFormatter coerce(CoercionTarget type, String value) {
    return DateTimeFormatter.ofPattern(value);
  }

  @Override
  public boolean isApplicableTo(CoercionTarget target) {
    return DateTimeFormatter.class.isAssignableFrom(target.getType());
  }

  @Override
  public boolean hasDefaultValue() {
    return true;
  }

  @Override
  public DateTimeFormatter getDefaultValue(CoercionTarget target) {
    return DateTimeFormatter.ISO_DATE_TIME;
  }

}
