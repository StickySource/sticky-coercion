package net.stickycode.coercion.map;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;

import java.lang.reflect.Field;
import java.util.Map;

import net.stickycode.coercion.CoercionFinder;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.coercion.Coercions;
import net.stickycode.coercion.target.CoercionTargets;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MapCoercionTest {

  @Rule
  public TestName testName = new TestName();

  Map<String, String> empty;

  Map<String, String> singleString;

  Map<String, Integer> singleInteger;

  Map<Integer, Integer> integer;

  @Spy
  CoercionFinder finder = new Coercions();

  @InjectMocks
  MapCoercion coercion = new MapCoercion();

  @Test
  public void empty() {
    assertThat(coerce("")).isEmpty();
  }

  @Test
  public void singleString() {
    assertThat(coerce("a=b")).includes(entry("a", "b"));
  }

  @Test
  public void singleInteger() {
    assertThat(coerce("a=3")).includes(entry("a", 3));
    assertThat(coerce("b=-3")).includes(entry("b", -3));
    assertThat(coerce("b=-3,a=2")).includes(entry("a", 2), entry("b", -3));
  }

  @Test
  public void integer() {
    assertThat(coerce("5=3")).includes(entry(5, 3));
    assertThat(coerce("5=3,10=2")).includes(entry(5, 3), entry(10, 2));
  }

  private Map<Object, Object> coerce(String value) {
    return coercion.coerce(componentCoercionType(), value);
  }

  private CoercionTarget componentCoercionType() {
    String name = testName.getMethodName();
    Field field = getField(name);
    return CoercionTargets.find(field);
  }

  private Field getField(String name) {
    try {
      return getClass().getDeclaredField(name);
    }
    catch (SecurityException e) {
      throw new RuntimeException(e);
    }
    catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }
}
