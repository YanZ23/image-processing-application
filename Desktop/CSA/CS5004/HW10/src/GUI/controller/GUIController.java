package GUI.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

import javax.imageio.ImageIO;

import GUI.view.IView;
import model.IImage;
import model.IImageDataBase;
import model.IImageState;
import model.ImageImpl;

/**
 * GUI Controller class implementing the IGUIController interface.
 * Handles view events and manages the interaction between the view and the model.
 */
public class GUIController implements IGUIController {
  private final IImageDataBase model;
  private final IView view;

  /**
   * Creates a new GUIController with the given model and view.
   *
   * @param model The image database model.
   * @param view The GUI view.
   */
  public GUIController(IImageDataBase model, IView view) {
    this.model = Objects.requireNonNull(model);
    this.view = Objects.requireNonNull(view);
    this.view.addViewListener(this);
  }

  /**
   * Starts the GUI.
   */
  @Override
  public void run() {
    view.setVisible(true);
  }

  /**
   * Converts an IImageState object to a BufferedImage.
   *
   * @param imageState The IImageState object to be converted.
   * @return A BufferedImage representation of the IImageState object.
   */
  private BufferedImage convertToBufferedImage(IImageState imageState) {
    int width = imageState.getWidth();
    int height = imageState.getHeight();

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int red = imageState.getRedChannel(col, row);
        int green = imageState.getGreenChannel(col, row);
        int blue = imageState.getBlueChannel(col, row);

        int rgb = (red << 16) | (green << 8) | blue;
        image.setRGB(col, row, rgb);
      }
    }

    return image;
  }

  @Override
  public IImageState handleLoadEvent(String sourceId) {
    try {
      File file = new File(sourceId);
      if (!file.exists()) {
        throw new FileNotFoundException("File " + sourceId + " not found!");
      }

      BufferedImage image;
      String extension = sourceId.substring(sourceId.lastIndexOf(".") + 1);
      if (extension.equalsIgnoreCase("ppm")) {
        image = readPPM(sourceId);
      } else {
        image = ImageIO.read(file);
      }

      int width = image.getWidth();

      int height = image.getHeight();


      IImage newImage = new ImageImpl(width, height);

      for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
          int pixel = image.getRGB(col, row);
          int r = (pixel >> 16) & 0xFF;
          int g = (pixel >> 8) & 0xFF;
          int b = pixel & 0xFF;

          newImage.setPixel(col, row, r, g, b);
        }
      }
      BufferedImage bufferedImage = convertToBufferedImage(newImage);
      view.renderImage(bufferedImage);

      return newImage;
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  @Override
  public void handleSaveEvent(IImageState sourceImage, String pathToSave) {
    int height = sourceImage.getHeight();
    int width = sourceImage.getWidth();

    try {
      String extension = pathToSave.substring(pathToSave.lastIndexOf(".") + 1);

      if (extension.equalsIgnoreCase("ppm")) {
        writePPM(sourceImage, pathToSave);
      } else {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < height; row++) {
          for (int col = 0; col < width; col++) {
            int red = sourceImage.getRedChannel(col, row);
            int green = sourceImage.getGreenChannel(col, row);
            int blue = sourceImage.getBlueChannel(col, row);


            int rgb = (red << 16) | (green << 8) | blue;
            image.setRGB(col, row, rgb);
          }
        }

        File file = new File(pathToSave);
        ImageIO.write(image, extension, file);
      }
    } catch (IOException e) {
      throw new IllegalStateException("Failed to save PNG image.");
    }
  }

  /**
   * Reads a PPM image file and returns a BufferedImage representation.
   *
   * @param filename The path of the PPM file to be read.
   * @return A BufferedImage representation of the PPM image.
   * @throws IOException if an error occurs during reading the file.
   */
  private BufferedImage readPPM(String filename) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line = reader.readLine();

      if (!line.equals("P3")) {
        throw new IllegalArgumentException("Invalid PPM file: " + filename);
      }

      line = reader.readLine();
      while (line.trim().startsWith("#")) {
        line = reader.readLine();
      }

      String[] dimensions = line.split("\\s+");
      int width = Integer.parseInt(dimensions[0]);
      int height = Integer.parseInt(dimensions[1]);

      line = reader.readLine();
      while (line.trim().startsWith("#")) {
        line = reader.readLine();
      }
      int maxColor = Integer.parseInt(line);

      if (maxColor != 255) {
        throw new IllegalArgumentException("Invalid max color value in PPM file: " + filename);
      }

      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          int red = nextNumber(reader);
          int green = nextNumber(reader);
          int blue = nextNumber(reader);

          int rgb = (red << 16) | (green << 8) | blue;
          image.setRGB(x, y, rgb);
        }
      }
      return image;
    }
  }

  /**
   * Reads the next number from a BufferedReader. This method is used for parsing PPM image data.
   *
   * @param reader The BufferedReader from which to read the number.
   * @return The next number read from the BufferedReader.
   * @throws IOException if an error occurs during reading.
   */
  private int nextNumber(BufferedReader reader) throws IOException {
    StringBuilder builder = new StringBuilder();
    char c;
    while (true) {
      c = (char) reader.read();
      if (Character.isDigit(c)) {
        builder.append(c);
      } else if (builder.length() > 0) {
        break;
      }
    }
    return Integer.parseInt(builder.toString());
  }

  /**
   * Writes an IImageState object to a PPM file at a given path.
   *
   * @param imageState The IImageState object to be written.
   * @param pathToSave The path where the PPM file will be saved.
   * @throws IOException if an error occurs during writing the file.
   */
  private void writePPM(IImageState imageState, String pathToSave) throws IOException {
    int height = imageState.getHeight();
    int width = imageState.getWidth();

    PrintWriter writer = new PrintWriter(new FileWriter(pathToSave));

    writer.println("P3");
    writer.println(width + " " + height);
    writer.println("255");

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int red = imageState.getRedChannel(col, row);
        int green = imageState.getGreenChannel(col, row);
        int blue = imageState.getBlueChannel(col, row);

        writer.println(red + " " + green + " " + blue);
      }
    }
    writer.close();
  }
}
