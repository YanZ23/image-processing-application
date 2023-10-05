package controller.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import model.IImage;
import model.IImageState;
import model.ImageImpl;
import view.StringAppendable;

/**
 * The implementation of IImageLoader to load images from PNG format files.
 */
public class PNGImageLoader implements IImageLoader {
  private final String filePath;
  private final StringAppendable output;

  /**
   * Constructs a PNGImageLoader with the specified file path and output appendable.
   *
   * @param filePath The path to the PNG format file.
   * @param output   The appendable used to display messages during image loading.
   */
  public PNGImageLoader(String filePath, StringAppendable output) {
    this.filePath = Objects.requireNonNull(filePath);
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
  public IImageState run(String sourceId) {
    try {
      File file = new File(filePath);
      if (!file.exists()) {
        throw new FileNotFoundException("File " + sourceId + " not found!");
      }
      BufferedImage image = ImageIO.read(file);

      int width = image.getWidth();
      write("Width of image: " + width + "\n");
      int height = image.getHeight();
      write("Height of image: " + height + "\n");

      IImage newImage = new ImageImpl(width, height);

      for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
          int pixel = image.getRGB(col, row);
          int r = (pixel >> 16) & 0xFF;
          int g = (pixel >> 8) & 0xFF;
          int b = pixel & 0xFF;
          write("Color of pixel (" + col + "," + row + "): " + r + "," + g + "," + b + "\n");
          newImage.setPixel(col, row, r, g, b);
        }
      }
      return newImage;
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }
}
