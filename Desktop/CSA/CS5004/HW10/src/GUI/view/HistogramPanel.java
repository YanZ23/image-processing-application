package GUI.view;

import java.awt.*;

import javax.swing.*;

/**
 * Custom JPanel for displaying a histogram of color values.
 * The histogram can be updated by calling the setHistogram method.
 */
public class HistogramPanel extends JPanel {
  private int[][] histograms;

  /**
   * Sets the current histograms to be displayed and repaints the panel.
   *
   * @param histograms The new histograms to be displayed.
   */
  public void setHistogram(int[][] histograms) {
    this.histograms = histograms;
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (histograms != null) {
      int width = getWidth();
      int height = getHeight();

      g.setColor(Color.WHITE);
      g.fillRect(0, 0, width, height);

      int barWidth = width / histograms[0].length;

      Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.BLACK};

      for (int h = 0; h < histograms.length; h++) {
        int[] histogram = histograms[h];
        g.setColor(colors[h]);

        int maxCount = getMaxCount(histogram);
        for (int i = 0; i < histogram.length; i++) {
          int barHeight = (int) (((double) histogram[i] / maxCount) * height);
          int x = i * barWidth;
          int y = height - barHeight;
          g.fillRect(x, y, barWidth, barHeight);
        }
      }
    }
  }

  /**
   * Helper method to get the maximum count value in a histogram.
   *
   * @param histogram The histogram to process.
   * @return The maximum count value in the histogram.
   */
  private int getMaxCount(int[] histogram) {
    int maxCount = 0;
    for (int count : histogram) {
      if (count > maxCount) {
        maxCount = count;
      }
    }
    return maxCount;
  }
}

