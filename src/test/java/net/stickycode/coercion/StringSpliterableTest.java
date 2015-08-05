package net.stickycode.coercion;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class StringSpliterableTest {

  @Test
  public void plain() {
    assertThat(new StringSpliterable("a,b,c")).containsOnly("a", "b", "c");
  }

  @Test
  public void semi() {
    assertThat(new StringSpliterable("[;]a;b;c")).containsOnly("a", "b", "c");
  }

  @Test
  public void dot() {
    assertThat(new StringSpliterable("[.]a.b.c")).containsOnly("a", "b", "c");
  }

  @Test
  public void tooManyDelimiters() {
    assertThat(new StringSpliterable("[.,]a.b.c")).containsOnly("[.", "]a.b.c");
  }

  @Test
  public void onlyFirstBracket() {
    assertThat(new StringSpliterable("[.a.b.c")).containsOnly("[.a.b.c");
  }

  @Test
  public void onlyLastBracket() {
    assertThat(new StringSpliterable(".]a.b.c")).containsOnly(".]a.b.c");
  }

  @Test
  public void firstDelimiter() {
    assertThat(new StringSpliterable(",a,b,c")).containsOnly("", "a", "b", "c");
  }

  @Test
  public void firstTwoDelimiters() {
    assertThat(new StringSpliterable(",,a,b,c")).containsOnly("", "a", "b", "c").hasSize(5);
  }
  
  @Test
  public void firstAlternateDelimiter() {
    assertThat(new StringSpliterable("[.].a.b.c")).containsOnly("", "a", "b", "c").hasSize(4);
  }
  
  @Test
  public void firstAlternateDelimiters() {
    assertThat(new StringSpliterable("[.]..a.b.c")).containsOnly("", "a", "b", "c").hasSize(5);
  }

}
