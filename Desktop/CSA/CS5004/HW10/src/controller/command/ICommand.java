package controller.command;

import java.util.Scanner;

import model.IImageDataBase;

/**
 * The interface representing a command for image manipulation.
 */
public interface ICommand {
  /**
   * Executes the image manipulation command based on the provided input and model.
   *
   * @param scanner The input scanner containing command arguments.
   * @param model   The image database to store and retrieve images.
   */
  void run(Scanner scanner, IImageDataBase model);
}
