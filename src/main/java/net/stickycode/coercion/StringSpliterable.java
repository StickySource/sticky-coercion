package net.stickycode.coercion;

import java.util.ArrayList;
import java.util.Iterator;

public class StringSpliterable
    implements Iterable<String> {

  private String value;

  private char delimiter = ',';

  public StringSpliterable(String value) {
    super();
    this.value = processDelimiter(value);
  }

  private String processDelimiter(String value) {
    if (value.length() < 3)
      return value;

    if (value.charAt(0) != '[')
      return value;

    if (value.charAt(2) != ']')
      return value;

    this.delimiter = value.charAt(1);
    return value.substring(3);
  }

  @Override
  public Iterator<String> iterator() {
    return new StringSpliterator(delimiter, value);
  }

  public String[] asArray() {
    ArrayList<String> list = new ArrayList<String>();
    for (String string : this) {
      list.add(string);
    }
    return list.toArray(new String[list.size()]);
  }

}
