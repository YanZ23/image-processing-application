package controller.command;

import java.util.Objects;
import java.util.Scanner;

import model.IImageDataBase;
import model.IImageState;
import model.transformation.ColorTransformationsFilter;
import model.transformation.ITransformation;

/**
 * The command that uses filter to transform an image into sepia tone.
 */
public class SepiaToneFilterCommand implements ICommand {
  private final IImageDataBase model;

  /**
   * Instantiates a new SepiaToneFilterCommand with the specified image database.
   *
   * @param model The image database to store and retrieve images.
   */
  public SepiaToneFilterCommand(IImageDataBase model) {
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

    double [][] sepiaToneGreyscaleMatrix = {
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}
    };

    ITransformation greyscaleFilterTransformation =
            new ColorTransformationsFilter(sepiaToneGreyscaleMatrix);

    IImageState filteredImage = greyscaleFilterTransformation.run(sourceImage);

    this.model.add(destId, filteredImage);
  }
}
