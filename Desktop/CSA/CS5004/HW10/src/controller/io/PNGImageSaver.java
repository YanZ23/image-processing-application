package controller.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import model.IImageState;
import view.StringAppendable;

/**
 * The implementation of IImageSaver to save images to PNG format files.
 */
public class PNGImageSaver implements IImageSaver {
  private final String pathToSave;
  private final StringAppendable output;

  /**
   * Constructs a PNGImageSaver with the specified file path and output appendable.
   *
   * @param pathToSave The path where the PNG format file will be saved.
   * @param output     The appendable used to display messages during image saving.
   */
  public PNGImageSaver(String pathToSave, StringAppendable output) {
    this.pathToSave = Objects.requireNonNull(pathToSave);
    this.output = Objects.requireNonNull(output);
  }

  private void write(String message) {
    try {
      this.output.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("Writing failed.");
    }
  }

  @Override
  public void run(IImageState sourceImage) {
    int height = sourceImage.getHeight();
    int width = sourceImage.getWidth();

    try {
      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
          int red = sourceImage.getRedChannel(col, row);
          int green = sourceImage.getGreenChannel(col, row);
          int blue = sourceImage.getBlueChannel(col, row);
          write(red + "\n" + green + "\n" + blue + "\n");

          int rgb = (red << 16) | (green << 8) | blue;
          image.setRGB(col, row, rgb);
        }
      }

      File file = new File(pathToSave);
      ImageIO.write(image, "png", file);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to save PNG image.");
    }
  }
}
