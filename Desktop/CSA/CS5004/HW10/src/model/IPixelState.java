package model;

/**
 * The interface representing the state of a pixel in an image.
 */
public interface IPixelState {
  /**
   * Gets the red channel value of the pixel.
   *
   * @return the red channel value of the pixel.
   */
  int getR();

  /**
   * Gets the green channel value of the pixel.
   *
   * @return the green channel value of the pixel.
   */
  int getG();

  /**
   * Gets the blue channel value of the pixel.
   *
   * @return the blue channel value of the pixel.
   */
  int getB();
}
