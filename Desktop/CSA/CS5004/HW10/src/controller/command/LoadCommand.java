package controller.command;

import java.util.Objects;
import java.util.Scanner;

import controller.io.IImageLoader;
import controller.io.JPGImageLoader;
import controller.io.PNGImageLoader;
import controller.io.PPMImageLoader;
import model.IImageDataBase;
import model.IImageState;
import view.StringAppendable;

/**
 * A command that loads an image from different formats file and adds it to the image database.
 */
public class LoadCommand implements ICommand {
  private final IImageDataBase model;
  private IImageState image;
  private StringAppendable output;

  /**
   * Constructs a LoadCommand with the specified image database.
   *
   * @param model The image database where the loaded image will be added.
   */
  public LoadCommand(IImageDataBase model) {
    this.model = model;
  }

  /**
   * Sets the output appendable to display messages during image loading.
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

    String sourceId = scanner.next();

    if (!scanner.hasNext()) {
      throw new IllegalStateException("Third argument must be the image id.");
    }

    String destId = scanner.next();

    output.clear();

    if (sourceId.contains(".ppm")) {
      IImageLoader ppmImageLoader = new PPMImageLoader(sourceId, output);

      IImageState loadedImage = ppmImageLoader.run(sourceId);

      this.model.add(destId, loadedImage);
    } else if (sourceId.contains(".png")) {
      IImageLoader pngImageLoader = new PNGImageLoader(sourceId, output);

      IImageState loadedImage = pngImageLoader.run(sourceId);

      this.model.add(destId, loadedImage);
    } else if (sourceId.contains(".jpg")) {
      IImageLoader jpgImageLoader = new JPGImageLoader(sourceId, output);

      IImageState loadedImage = jpgImageLoader.run(sourceId);

      this.model.add(destId, loadedImage);
    } else {
      throw new IllegalStateException("Unsupported file format.");
    }
  }
}
