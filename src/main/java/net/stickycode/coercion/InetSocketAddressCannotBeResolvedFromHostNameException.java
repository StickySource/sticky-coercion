package net.stickycode.coercion;

import java.net.UnknownHostException;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class InetSocketAddressCannotBeResolvedFromHostNameException
    extends PermanentException {

  public InetSocketAddressCannotBeResolvedFromHostNameException(UnknownHostException e, String hostname) {
    super(e, "Could not resolve {} to an ip address", hostname);
  }

}
