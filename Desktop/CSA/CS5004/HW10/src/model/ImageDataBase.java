package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The class implementing the IImageDataBase interface, representing a database of images.
 */
public class ImageDataBase implements IImageDataBase {
  private final Map<String, IImageState> images;

  /**
   * Instantiates a new Image data base.
   */
  public ImageDataBase() {
    this.images = new HashMap<String, IImageState>();
  }

  @Override
  public void add(String id, IImageState image) {
    if (id == null || image == null) {
      throw new IllegalArgumentException("Id or image is null");
    }
    this.images.put(id, image);
  }

  @Override
  public IImageState get(String id) {
    Objects.requireNonNull(id);
    return this.images.get(id);
  }
}
