package model;

/**
 * The class implementing the IImageState interface, representing an image
 * with read-only capabilities.
 */
public class ImageImpl implements IImage {
  private final int width;
  private final int height;
  private final IPixel[][] data;

  /**
   * Instantiates a new Image.
   *
   * @param width  the width of the image.
   * @param height the height of the image.
   */
  public ImageImpl(int width, int height) {

    this.width = width;
    this.height = height;
    this.data = new Pixel[height][width];
  }


  @Override
  public void setPixel(int x, int y, int r, int g, int b) {
    if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
      throw new IllegalArgumentException("x or y outside of bounds.");
    }
    this.data[y][x] = new Pixel(r, g, b);
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getRedChannel(int x, int y) {
    if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
      throw new IllegalArgumentException("x or y outside of bounds.");
    }
    return this.data[y][x].getR();
  }

  @Override
  public int getBlueChannel(int x, int y) {
    if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
      throw new IllegalArgumentException("x or y outside of bounds.");
    }
    return this.data[y][x].getB();
  }

  @Override
  public int getGreenChannel(int x, int y) {
    if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
      throw new IllegalArgumentException("x or y outside of bounds.");
    }
    return this.data[y][x].getG();
  }

}
