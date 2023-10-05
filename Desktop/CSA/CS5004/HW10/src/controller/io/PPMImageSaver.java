package controller.io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import model.IImageState;
import view.StringAppendable;

/**
 * The implementation of IImageSaver to save images to PPM format files.
 */
public class PPMImageSaver implements IImageSaver {
  private final String pathToSave;
  private final StringAppendable output;

  /**
   * Constructs a PPMImageSaver with the specified file path and output appendable.
   *
   * @param pathToSave The path where the PPM format file will be saved.
   * @param output     The appendable used to display messages during image saving.
   */
  public PPMImageSaver(String pathToSave, StringAppendable output) {
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
    write("P3\n");
    write(width + " " + height + "\n");
    write("255\n");

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int red = sourceImage.getRedChannel(col, row);
        int green = sourceImage.getGreenChannel(col, row);
        int blue = sourceImage.getBlueChannel(col, row);

        write(red + "\n" + green + "\n" + blue + "\n");
      }
    }

    try (FileWriter writer = new FileWriter(pathToSave)) {
      writer.write(output.getOutput());
    } catch (IOException e) {
      throw new IllegalStateException("Failed to write to file.");
    }
  }
}
