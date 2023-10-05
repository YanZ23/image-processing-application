package model;

/**
 * The interface of the current state of the image.
 */
public interface IImageState {
  /**
   * Gets height of image.
   *
   * @return the height
   */
  int getHeight();

  /**
   * Gets width of image.
   *
   * @return the width
   */
  int getWidth();

  /**
   * Gets red channel value at the specified coordinates.
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the red channel value.
   */
  int getRedChannel(int x, int y);

  /**
   * Gets blue channel value at the specified coordinates.
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the blue channel value.
   */
  int getBlueChannel(int x, int y);

  /**
   * Gets green channel value at the specified coordinates.
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the green channe lvalue.
   */
  int getGreenChannel(int x, int y);
}
