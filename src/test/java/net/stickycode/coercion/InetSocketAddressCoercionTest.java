package net.stickycode.coercion;

import static org.fest.assertions.Assertions.assertThat;

import java.net.InetSocketAddress;
import java.util.regex.Pattern;

import org.junit.Test;

public class InetSocketAddressCoercionTest {

  @Test
  public void applicability() {
    assertThat(new InetSocketAddressCoercion().isApplicableTo(coercionTarget())).isTrue();
    assertThat(new InetSocketAddressCoercion().isApplicableTo(new CoercionType(String.class))).isFalse();
    assertThat(new InetSocketAddressCoercion().isApplicableTo(new CoercionType(Pattern.class))).isFalse();
  }

  private CoercionType coercionTarget() {
    return new CoercionType(InetSocketAddress.class);
  }

  @Test(expected = InetSocketAddressSpecificationInvalidException.class)
  public void blankCheck() {
    new InetSocketAddressCoercion().coerce(coercionTarget(), "");
  }

  @Test(expected = InetSocketAddressSpecificationInvalidException.class)
  public void noPort() {
    new InetSocketAddressCoercion().coerce(coercionTarget(), "localhost");
  }

  @Test(expected = InetSocketAddressSpecificationInvalidException.class)
  public void noPortAfterColon() {
    new InetSocketAddressCoercion().coerce(coercionTarget(), "localhost:");
  }
  
  @Test(expected = InetSocketAddressCannotBeResolvedFromHostNameException.class)
  public void unresolvableAddress() {
    new InetSocketAddressCoercion().coerce(coercionTarget(), "doesnotexistanywhere.:70");
  }
  
  @Test(expected = InetSocketAddressCannotBeResolvedFromHostNameException.class)
  public void madeUpAddress() {
    new InetSocketAddressCoercion().coerce(coercionTarget(), "doesn=ot+ ex&istanywhere.:70");
  }
  
  @Test
  public void local() {
    new InetSocketAddressCoercion().coerce(coercionTarget(), "127.0.0.1:70");
    new InetSocketAddressCoercion().coerce(coercionTarget(), "::1:70");
  }
  
  @Test
  public void localhost() {
    new InetSocketAddressCoercion().coerce(coercionTarget(), "localhost:70");
  }
  
  @Test
  public void ipv6() {
    new InetSocketAddressCoercion().coerce(coercionTarget(), "fe80::21a:73ff:fe88:e216:70");
  }
}
