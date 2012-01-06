package net.stickycode.coercion;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class InetSocketAddressSpecificationInvalidException
    extends PermanentException {

  public InetSocketAddressSpecificationInvalidException(String value) {
    super("Expected host:port or X.X.X.X:port or #:#:#:#:#:#:#:#:port but found {} when parsing socket address configuration", value);
  }

  public InetSocketAddressSpecificationInvalidException(NumberFormatException e, String value) {
    super(e, "Port was not specified correctly or missing. Expected host:port or X.X.X.X:port or #:#:#:#:#:#:#:#:port but found {} when parsing port part of socket address configuration", value);
  }

}
