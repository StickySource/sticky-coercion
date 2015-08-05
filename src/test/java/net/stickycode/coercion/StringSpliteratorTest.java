package net.stickycode.coercion;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class StringSpliteratorTest {

  @Test
  public void empty() {
    assertThat(new StringSpliterator(',', "")).isEmpty();
  }

  @Test
  public void single() {
    assertThat(new StringSpliterator(',', "abc")).containsOnly("abc");
  }

  @Test
  public void twoValues() {
    assertThat(new StringSpliterator(',', "abc,cba")).containsOnly("abc", "cba");
  }

  @Test
  public void threeValues() {
    assertThat(new StringSpliterator(',', "abc,cba,blah")).containsOnly("abc", "cba", "blah");
  }
  
  @Test
  public void threeValuesOneEscaped() {
    assertThat(new StringSpliterator(',', "abc,c\\,ba,blah")).containsOnly("abc", "c,ba", "blah");
  }
  
  @Test
  public void alternateDelimiter() {
    assertThat(new StringSpliterator(';', "abc;c\\;ba;blah")).containsOnly("abc", "c;ba", "blah");
  }
  
  @Test
  public void alternateDelimiterWithCommas() {
    assertThat(new StringSpliterator(';', "abc;c,ba;blah")).containsOnly("abc", "c,ba", "blah");
  }
  
}
