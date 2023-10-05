package controller.command;

import java.util.Objects;
import java.util.Scanner;

import controller.io.IImageSaver;
import controller.io.JPGImageSaver;
import controller.io.PNGImageSaver;
import controller.io.PPMImageSaver;
import model.IImageDataBase;
import model.IImageState;
import view.StringAppendable;

/**
 * A command that saves an image from the image database to the different formats file.
 */
public class SaveCommand implements ICommand {
  private IImageDataBase model;
  private IImageState image;
  private StringAppendable output;

  /**
   * Sets the output appendable to display messages during image saving.
   *
   * @param output The output appendable to set.
   */
  public void setOutput(StringAppendable output) {
    this.output = output;
  }

  @Override
  public void run(Scanner scanner, IImageDataBase model) {
    Objects.requireNonNull(scanner);
    Objects.requireNonNull(model);

    if (!scanner.hasNext()) {
      throw new IllegalStateException("Second argument must be the image id.");
    }

    String savedId = scanner.next();

    if (!scanner.hasNext()) {
      throw new IllegalStateException("Third argument must be the image id.");
    }

    String sourceId = scanner.next();

    IImageState sourceImage = model.get(sourceId);
    if (sourceImage == null) {
      throw new IllegalStateException("Image with the specified id doesn't exist.");
    }

    output.clear();

    if (savedId.contains(".ppm")) {
      IImageSaver ppmImageSaver = new PPMImageSaver(savedId, output);
      ppmImageSaver.run(sourceImage);
    } else if (savedId.contains(".png")) {
      IImageSaver pngImageSaver = new PNGImageSaver(savedId, output);
      pngImageSaver.run(sourceImage);
    } else if (savedId.contains(".jpg")) {
      IImageSaver jpgImageSaver = new JPGImageSaver(savedId, output);
      jpgImageSaver.run(sourceImage);
    } else {
      throw new IllegalStateException("Unsupported file format.");
    }
  }
}
