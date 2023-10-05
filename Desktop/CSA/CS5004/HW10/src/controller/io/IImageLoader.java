package controller.io;

import model.IImageState;

/**
 * The interface representing an image loader.
 */
public interface IImageLoader {
  /**
   * Loads an image from the specified source.
   *
   * @param sourceId The unique identifier of the image source.
   * @return The loaded image state.
   */
  IImageState run(String sourceId);
}
