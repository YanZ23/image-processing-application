package model;

/**
 * The class implementing the IPixel interface, representing a pixel in an image.
 */
public class Pixel implements IPixel {
  private int r;
  private int g;
  private int b;

  /**
   * Instantiates a new Pixel with the specified channel values.
   *
   * @param r the red channel value (0-255).
   * @param g the green channel value (0-255).
   * @param b the blue channel value (0-255).
   */
  public Pixel(int r, int g, int b) {
    if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
      throw new IllegalArgumentException("Pixel values out of bounds.");
    }
    this.r = r;
    this.g = g;
    this.b = b;
  }

  @Override
  public int getR() {
    return r;
  }

  @Override
  public void setR(int val) {
    if (val < 0 || val > 255) {
      throw new IllegalArgumentException("Channel value is invalid");
    }

    this.r = val;
  }

  @Override
  public int getG() {
    return g;
  }

  @Override
  public void setG(int val) {
    if (val < 0 || val > 255) {
      throw new IllegalArgumentException("Channel value is invalid");
    }

    this.g = val;
  }

  @Override
  public int getB() {
    return b;
  }

  @Override
  public void setB(int val) {
    if (val < 0 || val > 255) {
      throw new IllegalArgumentException("Channel value is invalid");
    }

    this.b = val;
  }

  @Override
  public String toString() {
    return this.r + " " + this.g + " " + this.b;
  }
}
