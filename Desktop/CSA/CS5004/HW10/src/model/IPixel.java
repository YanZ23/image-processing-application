package model;

/**
 * The interface representing a pixel with read and write capabilities.
 */
public interface IPixel extends IPixelState {
  /**
   * Sets the red channel value of the pixel.
   *
   * @param val the red value.
   */
  void setR(int val);

  /**
   * Sets the green channel value of the pixel.
   *
   * @param val the green val.
   */
  void setG(int val);

  /**
   * Sets the blue channel value of the pixel.
   *
   * @param val the blue val.
   */
  void setB(int val);
}
