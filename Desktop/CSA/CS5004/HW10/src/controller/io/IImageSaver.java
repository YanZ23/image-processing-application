package controller.io;

import model.IImageState;

/**
 * The interface representing an image saver.
 */
public interface IImageSaver {
  /**
   * Saves the specified image state.
   *
   * @param sourceImage The image state to be saved.
   */
  void run(IImageState sourceImage);
}

