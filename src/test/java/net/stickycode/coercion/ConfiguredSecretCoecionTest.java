package net.stickycode.coercion;


public class ConfiguredSecretCoecionTest
    extends AbstractCoercionTest {

  @Override
  protected Coercion<?> coercion() {
    return new ConfiguredSecretCoercion();
  }

  @Override
  protected String firstValue() {
    return null;
  }

  @Override
  protected String secondValue() {
    return null;
  }

  @Override
  protected String thirdValue() {
    return null;
  }

  @Override
  protected Object firstResult() {
    return null;
  }

  @Override
  protected Object secondResult() {
    return null;
  }

  @Override
  protected Object thirdResult() {
    return null;
  }

  @Override
  protected Class<?> getInapplicableType() {
    return null;
  }

  @Override
  protected Class<?> getApplicableType() {
    return null;
  }

  @Override
  protected Class<?> getApplicableArrayType() {
    return null;
  }

}
