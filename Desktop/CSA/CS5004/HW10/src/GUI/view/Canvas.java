package GUI.view;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

/**
 * Custom JPanel for displaying an image. The image can be updated by calling
 * the renderImage method.
 */
public class Canvas extends JPanel {
  private BufferedImage image;

  /**
   * Default constructor.
   */
  public Canvas() {
  }

  @Override
  public void paintComponent(Graphics g) {
    System.out.println("paintComponent called");
    super.paintComponent(g);
    g.drawImage(this.image, 0, 0, null);
  }

  /**
   * Sets the current image to be displayed and repaints the panel.
   *
   * @param image The new image to be displayed.
   */
  public void renderImage(BufferedImage image) {
    System.out.println("renderImage called with " + (image == null ? "null" : "non-null")
            + " argument");
    if (image != null) {
      System.out.println("Image width: " + image.getWidth());
      System.out.println("Image height: " + image.getHeight());
    }
    this.image = image;
    repaint();
  }
}
