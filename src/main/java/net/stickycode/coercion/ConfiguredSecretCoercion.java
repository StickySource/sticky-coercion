package net.stickycode.coercion;

import net.stickycode.stereotype.configured.ConfiguredSecret;

public class ConfiguredSecretCoercion
    implements Coercion<ConfiguredSecret> {

  @Override
  public ConfiguredSecret coerce(CoercionTarget type, String value) {
    return null;
  }

  @Override
  public boolean isApplicableTo(CoercionTarget target) {
    return false;
  }

  @Override
  public boolean hasDefaultValue() {
    return false;
  }

  @Override
  public ConfiguredSecret getDefaultValue(CoercionTarget target) {
    return null;
  }


}
