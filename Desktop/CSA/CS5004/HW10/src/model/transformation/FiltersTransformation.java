package model.transformation;

import java.util.Objects;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * A transformation that applies a general filter matrix to transform an image.
 * This transformation operates on each pixel of the source image and applies the specified
 * filter matrix to produce a new image.
 */
public class FiltersTransformation implements ITransformation {
  private final double[][] matrix;

  /**
   * Constructs a new FiltersTransformation with the specified filter matrix.
   *
   * @param matrix The filter matrix to be applied during the transformation.
   *               The matrix must be a square 2D array of odd size (e.g., 3x3, 5x5).
   *               Each element represents a weight for the corresponding pixel in the
   *               source image. The size of the matrix determines the size of the filter.
   *               The filter's anchor point is at the center of the matrix.
   */
  public FiltersTransformation(double[][] matrix) {
    this.matrix = Objects.requireNonNull(matrix);
    if (matrix.length < 3 || matrix.length % 2 == 0 || matrix[0].length != matrix.length) {
      throw new IllegalArgumentException("Invalid filter size. The filter must be a square matrix "
              + "of odd size, 3 or bigger.");
    }
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

    int filterSize = matrix.length;
    int filterHalfSize = filterSize / 2;

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;

        for (int ky = 0; ky < filterSize; ky++) {
          for (int kx = 0; kx < filterSize; kx++) {
            int sourceX = x + (kx - filterHalfSize);
            int sourceY = y + (ky - filterHalfSize);

            if (sourceX >= 0 && sourceX < width && sourceY >= 0 && sourceY < height) {
              int pixel = sourceImage.getRedChannel(sourceX, sourceY);
              redSum += (int) (matrix[ky][kx] * pixel);

              pixel = sourceImage.getGreenChannel(sourceX, sourceY);
              greenSum += (int) (matrix[ky][kx] * pixel);

              pixel = sourceImage.getBlueChannel(sourceX, sourceY);
              blueSum += (int) (matrix[ky][kx] * pixel);
            }
          }
        }
        int newRed = clamp(redSum);
        int newGreen = clamp(greenSum);
        int newBlue = clamp(blueSum);

        newImage.setPixel(x, y, newRed, newGreen, newBlue);
      }
    }
    return newImage;
  }
}
