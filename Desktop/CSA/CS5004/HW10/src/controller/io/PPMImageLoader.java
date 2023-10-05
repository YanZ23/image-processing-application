package controller.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.IImage;
import model.IImageState;
import model.ImageImpl;
import view.StringAppendable;

/**
 * The implementation of IImageLoader to load images from PPM format files.
 */
public class PPMImageLoader implements IImageLoader {
  private final String filePath;
  private StringAppendable output;

  /**
   * Constructs a PPMImageLoader with the specified file path and output appendable.
   *
   * @param filePath The path to the PPM format file.
   * @param output   The appendable used to display messages during image loading.
   */
  public PPMImageLoader(String filePath, StringAppendable output) {
    this.filePath = Objects.requireNonNull(filePath);
    this.output = Objects.requireNonNull(output);
  }

  private void write(String message) {
    try {
      this.output.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("Writing failed.");
    }
  }

  @Override
  public IImageState run(String sourceId) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filePath));
    }
    catch (FileNotFoundException e) {
      throw new IllegalStateException("File " + sourceId + " not found!");
    }
    StringBuilder builder = new StringBuilder();

    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      write("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    write("Width of image: " + width + "\n");
    int height = sc.nextInt();
    write("Height of image: " + height + "\n");
    int maxValue = sc.nextInt();
    write("Maximum value of a color in this file (usually 255): " + maxValue + "\n");

    IImage newImage = new ImageImpl(width, height);

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        write("Color of pixel (" + col + "," + row + "): " + r + "," + g + "," + b + "\n");
        newImage.setPixel(col, row, r, g, b);
      }
    }
    return newImage;
  }
}
