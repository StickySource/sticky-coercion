package net.stickycode.coercion;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import net.stickycode.coercion.AbstractFailedToCoerceValueException;
import net.stickycode.coercion.Coercion;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.stereotype.component.StickyExtension;

@StickyExtension
public class InetSocketAddressCoercion
    implements Coercion<InetSocketAddress>
{

  @Override
  public InetSocketAddress coerce(CoercionTarget target, String value)
    throws AbstractFailedToCoerceValueException
  {
    int indexOfColon = value.lastIndexOf(':');
    if (indexOfColon < 0 || indexOfColon == value.length())
      throw new InetSocketAddressSpecificationInvalidException(value);

    InetAddress address = resolveAddress(value.substring(0, indexOfColon));
    return new InetSocketAddress(address, parsePort(value.substring(indexOfColon + 1)));
  }

  private int parsePort(String substring) {
    try {
      return Integer.parseInt(substring);
    }
    catch (NumberFormatException e) {
      throw new InetSocketAddressSpecificationInvalidException(e, substring);
    }
  }

  private InetAddress resolveAddress(String hostname)
  {
    try
    {
      return InetAddress.getByName(hostname);
    }
    catch (UnknownHostException e)
    {
      throw new InetSocketAddressCannotBeResolvedFromHostNameException(e, hostname);
    }
  }

  @Override
  public boolean isApplicableTo(CoercionTarget target)
  {
    return target.getType().isAssignableFrom(InetSocketAddress.class);
  }

}
