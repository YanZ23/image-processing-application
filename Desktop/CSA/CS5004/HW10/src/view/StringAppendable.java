package view;

import java.io.IOException;

/**
 * The appendable class that writes to a StringBuilder and displays messages.
 */
public class StringAppendable implements Appendable {
  private final StringBuilder stringBuilder;

  /**
   * Instantiates a new String appendable.
   */
  public StringAppendable() {
    stringBuilder = new StringBuilder();
  }

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    stringBuilder.append(csq);
    System.out.print(csq);
    return this;
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    stringBuilder.append(csq, start, end);
    System.out.print(csq);
    return this;
  }

  @Override
  public Appendable append(char c) throws IOException {
    stringBuilder.append(c);
    System.out.print(c);
    return this;
  }

  /**
   * Gets the output string.
   *
   * @return the output
   */
  public String getOutput() {
    return stringBuilder.toString();
  }

  /**
   * Clears the contents of the StringBuilder.
   */
  public void clear() {
    stringBuilder.setLength(0);
  }
}
