package GUI.view;

import java.awt.image.BufferedImage;

/**
 * The IView interface is used to outline the necessary methods for a view in the MVC architecture.
 */
public interface IView {

  /**
   * Renders an image in the view.
   *
   * @param image the BufferedImage to be rendered
   */
  void renderImage(BufferedImage image);

  /**
   * Adds a listener to the view. The listener will be notified when certain events occur.
   *
   * @param listener the listener to be added
   */
  void addViewListener(ViewListener listener);

  /**
   * Sets the visibility of the view.
   *
   * @param value true if the view should be visible, false otherwise
   */
  void setVisible(boolean value);
}
