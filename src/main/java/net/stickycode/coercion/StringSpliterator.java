package net.stickycode.coercion;

import java.util.Iterator;

public class StringSpliterator
    implements Iterator<String> {

  private String value;

  private char delimiter = ',';

  private int current = 0;

  private int next = 0;

  public StringSpliterator(char delimiter, String value) {
    super();
    this.value = value;
    this.delimiter = delimiter;
  }

  @Override
  public boolean hasNext() {
    return current < value.length() && next >= 0;
  }

  @Override
  public String next() {
    try {
      this.next = findNext(current);
      int end = next >= 0 ? next : value.length();
      return value.substring(current, end).replaceAll("\\\\", "");
    }
    finally {
      this.current = next + 1;
    }
  }

  private int findNext(int point) {
    int index = value.indexOf(delimiter, point);
    if (index < 1)
      return index;
    
    // no escape
    if (value.charAt(index -1) != '\\')
      return index;
    
    // double escape
    if (value.charAt(index -2) == '\\')
      return index;
    
    // ignore escaped delimiter
    return findNext(index + 1);
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
