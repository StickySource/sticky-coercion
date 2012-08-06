package net.stickycode.coercion;

import java.nio.charset.Charset;

import net.stickycode.stereotype.plugin.StickyExtension;

@StickyExtension
public class CharacterSetCoercion
    implements Coercion<Charset> {

  @Override
  public Charset coerce(CoercionTarget target, String characterSetName) {
    if (Charset.isSupported(characterSetName))
      return Charset.forName(characterSetName);

    throw new UnableToCoerceAsCharsetIsNotSupported(target, characterSetName);
  }

  @Override
  public boolean isApplicableTo(CoercionTarget target) {
    return Charset.class.isAssignableFrom(target.getType());
  }

  @Override
  public boolean hasDefaultValue() {
    return true;
  }

  @Override
  public Charset getDefaultValue(CoercionTarget target) {
    return Charset.defaultCharset();
  }

}
