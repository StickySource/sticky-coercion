package net.stickycode.coercion.map;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.coercion.target.CoercionTargets;

import org.junit.Test;


public class MapCoercionApplicabilityTest {

  @Test
  public void applicableToMap() {
    assertThat(isApplicable(target(Map.class))).isTrue();
    assertThat(isApplicable(target(HashMap.class))).isTrue();
    assertThat(isApplicable(target(TreeMap.class))).isTrue();
  }
  
  @Test
  public void notApplicableToCollections() {
    assertThat(isApplicable(target(Collection.class))).isFalse();
    assertThat(isApplicable(target(List.class))).isFalse();
    assertThat(isApplicable(target(ArrayList.class))).isFalse();
    assertThat(isApplicable(target(Set.class))).isFalse();
    assertThat(isApplicable(target(HashSet.class))).isFalse();
  }

  private boolean isApplicable(CoercionTarget target) {
    return new MapCoercion().isApplicableTo(target);
  }

  private CoercionTarget target(Class<?> type) {
    return CoercionTargets.find(type);
  }
}
