package model;

/**
 * The interface representing a database of images.
 */
public interface IImageDataBase {
  /**
   * Add an image with the specified ID to the database.
   *
   * @param id    the id of the image.
   * @param image the image state to be added to the database.
   */
  void add(String id, IImageState image);

  /**
   * Get the image state with the specified ID from the database.
   *
   * @param id the id of the image to retrieve.
   * @return the image state associated with the provided ID, or null if not found.
   */
  IImageState get(String id);
}
