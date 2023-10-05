package controller.command;

import java.util.Objects;
import java.util.Scanner;

import model.IImageDataBase;
import model.IImageState;
import model.transformation.BrightnessTransformation;
import model.transformation.ITransformation;

/**
 * The command that transforms an image into brighter/darker.
 */
public class BrightnessAdjustCommand implements ICommand {
  private final IImageDataBase model;

  /**
   * Instantiates a new BrightnessAdjustCommand with the specified image database.
   *
   * @param model the model
   */
  public BrightnessAdjustCommand(IImageDataBase model) {
    this.model = model;
  }

  @Override
  public void run(Scanner scanner, IImageDataBase model) {
    Objects.requireNonNull(scanner);
    Objects.requireNonNull(model);

    if (!scanner.hasNextInt()) {
      throw new IllegalStateException("Second argument must be an int.");
    }

    int value = scanner.nextInt();

    if (!scanner.hasNext()) {
      throw new IllegalStateException("Third argument must be the image id.");
    }

    String sourceId = scanner.next();

    if (!scanner.hasNext()) {
      throw new IllegalStateException("Fourth argument must be the image id.");
    }

    String destId = scanner.next();

    IImageState sourceImage = model.get(sourceId);
    if (sourceImage == null) {
      throw new IllegalStateException("Image with the specified id doesn't exist.");
    }

    ITransformation brightnessTransformation = new BrightnessTransformation(value);

    IImageState brightenedImage = brightnessTransformation.run(sourceImage);

    this.model.add(destId, brightenedImage);
  }
}
