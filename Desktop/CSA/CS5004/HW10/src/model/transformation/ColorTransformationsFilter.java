package model.transformation;

import java.util.Objects;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * A transformation that applies a color filter matrix to transform an image.
 * This transformation operates on each pixel of the source image and applies the specified
 * color filter matrix to produce a new image.
 */
public class ColorTransformationsFilter implements ITransformation {
  private final double[][] matrix;

  /**
   * Constructs a new ColorTransformationsFilter with the specified color filter matrix.
   *
   * @param matrix The color filter matrix to be applied during the transformation.
   */
  public ColorTransformationsFilter(double[][] matrix) {
    this.matrix = Objects.requireNonNull(matrix);
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
    int width = sourceImage.getWidth();
    int height = sourceImage.getHeight();

    IImage newImage = new ImageImpl(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int red = sourceImage.getRedChannel(x, y);
        int green = sourceImage.getGreenChannel(x, y);
        int blue = sourceImage.getBlueChannel(x, y);

        int newRed = clamp((int) (matrix[0][0] * red + matrix[0][1] * green + matrix[0][2] * blue));
        int newGreen = clamp((int) (matrix[1][0] * red + matrix[1][1] * green
                + matrix[1][2] * blue));
        int newBlue = clamp((int) (matrix[2][0] * red + matrix[2][1] * green
                + matrix[2][2] * blue));


        newImage.setPixel(x, y, newRed, newGreen, newBlue);
      }
    }
    return newImage;
  }
}
