package model.transformation;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * The transformation that converts an image to value greyscale.
 */
public class ValueGreyscaleTransformation implements ITransformation {

  /**
   * Clamps the given value to be within the range [0, 255].
   *
   * @param value The value to be clamped.
   * @return The clamped value.
   */
  private int clamp(int value) {
    if (value < 0) {
      return 0;
    }
    if (value > 255) {
      return 255;
    }
    return value;
  }

  /**
   * Gets the value of a pixel in the given image state at the specified position.
   *
   * @param sourceImage The source image state.
   * @param row         The row index of the pixel.
   * @param col         The column index of the pixel.
   * @return The value of the pixel.
   */
  private int getImageValue(IImageState sourceImage, int row, int col) {
    int r = sourceImage.getRedChannel(col, row);
    int g = sourceImage.getGreenChannel(col, row);
    int b = sourceImage.getBlueChannel(col, row);
    int value = Math.max(Math.max(r, g), b);
    return clamp(value);
  }

  @Override
  public IImageState run(IImageState sourceImage) {
    IImage newImage = new ImageImpl(sourceImage.getWidth(), sourceImage.getHeight());
    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {
        int pixelValue = getImageValue(sourceImage, row, col);
        newImage.setPixel(col, row, pixelValue, pixelValue, pixelValue);
      }
    }
    return newImage;
  }
}
