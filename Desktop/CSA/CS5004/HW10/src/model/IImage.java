package model;

/**
 * The interface representing an image with read and write capabilities.
 */
public interface IImage extends IImageState {
  /**
   * Sets the pixel color at the specified coordinates.
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @param r the red channel value (0-255).
   * @param g the green channel value (0-255).
   * @param b the blue channel value (0-255).
   */
  void setPixel(int x, int y, int r, int g, int b);
}
