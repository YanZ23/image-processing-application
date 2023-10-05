package controller.command;

import java.util.Objects;
import java.util.Scanner;

import model.IImageDataBase;
import model.IImageState;
import model.transformation.FiltersTransformation;
import model.transformation.ITransformation;

/**
 * The command that sharpens an image.
 */
public class SharpenCommand implements ICommand {
  private final IImageDataBase model;

  /**
   * Instantiates a new SharpenCommand with the specified image database.
   *
   * @param model The image database to store and retrieve images.
   */
  public SharpenCommand(IImageDataBase model) {
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

    double [][] sharpenMatrix = {
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
    };

    ITransformation sharpenTransformation = new FiltersTransformation(sharpenMatrix);

    IImageState sharpenedImage = sharpenTransformation.run(sourceImage);

    this.model.add(destId, sharpenedImage);
  }
}
