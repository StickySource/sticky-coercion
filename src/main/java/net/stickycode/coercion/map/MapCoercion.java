package net.stickycode.coercion.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import net.stickycode.coercion.AbstractFailedToCoerceValueException;
import net.stickycode.coercion.Coercion;
import net.stickycode.coercion.CoercionFinder;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.stereotype.StickyPlugin;

@StickyPlugin
public class MapCoercion
    implements Coercion<Map<Object, Object>> {

  @Inject
  CoercionFinder finder;

  @Override
  public Map<Object, Object> coerce(CoercionTarget type, String value) throws AbstractFailedToCoerceValueException {
    if (value.length() == 0)
      return Collections.emptyMap();

    String[] entries = value.split(",");

    CoercionTarget[] typeArguments = type.getComponentCoercionTypes();
    assert typeArguments.length == 2 : "Maps should have two type arguments";
    
    Coercion<?> keyCoercion = findComponentCoercion(typeArguments[0]);
    Coercion<?> valueCoercion = findComponentCoercion(typeArguments[1]);
    Map<Object, Object> map = new HashMap<Object, Object>();
    for (String string : entries) {
      String[] s = string.split("=");
      map.put(
          keyCoercion.coerce(typeArguments[0], s[0]),
          valueCoercion.coerce(typeArguments[1], s[1]));
    }
    return Collections.unmodifiableMap(map);
  }

  private Coercion<?> findComponentCoercion(CoercionTarget target) {
    return finder.find(target);
  }

  @Override
  public boolean isApplicableTo(CoercionTarget target) {
    if (!Map.class.isAssignableFrom(target.getType()))
      return false;

    return true;
  }

}
