package GUI.view;

import model.IImageState;

/**
 * Listener interface for view events in the GUI. Provides methods to handle loading
 * and saving of images.
 */
public interface ViewListener {

  /**
   * Handles the event of loading an image from a source file.
   * Reads the image data from the given source file and creates an IImageState representation.
   *
   * @param sourceId The path of the source file.
   * @return An IImageState representation of the loaded image.
   */
  IImageState handleLoadEvent(String sourceId);

  /**
   * Handles the event of saving an image to a specified path.
   * Converts the given IImageState to an image file and writes it to the specified path.
   *
   * @param sourceImage The IImageState to be saved.
   * @param pathToSave  The path where the image file will be saved.
   */
  void handleSaveEvent(IImageState sourceImage, String pathToSave);
}
