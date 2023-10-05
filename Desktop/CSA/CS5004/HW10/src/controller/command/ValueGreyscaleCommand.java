package controller.command;

import java.util.Objects;
import java.util.Scanner;

import model.IImageDataBase;
import model.IImageState;
import model.transformation.ITransformation;
import model.transformation.ValueGreyscaleTransformation;

/**
 * The command that transforms an image into value greyscale.
 */
public class ValueGreyscaleCommand implements ICommand {
  private final IImageDataBase model;

  /**
   * Instantiates a new ValueGreyscaleCommand with the specified image database.
   *
   * @param model The image database to store and retrieve images.
   */
  public ValueGreyscaleCommand(IImageDataBase model) {
    this.model = model;
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

    IImageState sourceImage = model.get(sourceId);
    if (sourceImage == null) {
      throw new IllegalStateException("Image with the specified id doesn't exist.");
    }

    ITransformation valueGreyscaleTransformation = new ValueGreyscaleTransformation();

    IImageState greyedImage = valueGreyscaleTransformation.run(sourceImage);

    this.model.add(destId, greyedImage);

  }
}
