package model.transformation;

import model.IImageState;

/**
 * The interface representing an image transformation.
 */
public interface ITransformation {
  /**
   * Transforms the specified image state using the defined transformation.
   *
   * @param sourceImage The source image state to be transformed.
   * @return The transformed image state.
   */
  IImageState run(IImageState sourceImage);
}
