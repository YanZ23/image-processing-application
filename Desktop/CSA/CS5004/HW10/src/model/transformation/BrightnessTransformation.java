package model.transformation;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * The transformation that converts an image to brighter/darker.
 */
public class BrightnessTransformation implements ITransformation {
  private final int brightenValue;

  /**
   * Instantiates a new Brightness transformation.
   *
   * @param brightenValue the brighten value
   */
  public BrightnessTransformation(int brightenValue) {
    this.brightenValue = brightenValue;
  }

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

  @Override
  public IImageState run(IImageState sourceImage) {
    IImage newImage = new ImageImpl(sourceImage.getWidth(), sourceImage.getHeight());

    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {
        int newR = clamp(sourceImage.getRedChannel(col, row) + brightenValue);
        int newG = clamp(sourceImage.getGreenChannel(col, row) + brightenValue);
        int newB = clamp(sourceImage.getBlueChannel(col, row) + brightenValue);
        newImage.setPixel(col, row, newR, newG, newB);
      }
    }

    return newImage;
  }
}
