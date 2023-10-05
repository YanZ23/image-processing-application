package controller.command;

import java.util.Objects;
import java.util.Scanner;

import model.IImageDataBase;
import model.IImageState;
import model.transformation.ColorTransformationsFilter;
import model.transformation.ITransformation;

/**
 * The command that uses filter to transform an image into red greyscale.
 */
public class RedGreyscaleFilterCommand implements ICommand {
  private final IImageDataBase model;

  /**
   * Instantiates a new RedGreyscaleFilterCommand with the specified image database.
   *
   * @param model The image database to store and retrieve images.
   */
  public RedGreyscaleFilterCommand(IImageDataBase model) {
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

    double [][] redGreyscaleMatrix = {
            {1, 0, 0},
            {1, 0, 0},
            {1, 0, 0},
    };

    ITransformation greyscaleFilterTransformation =
            new ColorTransformationsFilter(redGreyscaleMatrix);

    IImageState filteredImage = greyscaleFilterTransformation.run(sourceImage);

    this.model.add(destId, filteredImage);
  }
}
