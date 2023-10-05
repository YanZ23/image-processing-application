package GUI.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.IImageDataBase;
import model.IImageState;
import model.transformation.BlueGreyscaleTransformation;
import model.transformation.BrightnessTransformation;
import model.transformation.ColorTransformationsFilter;
import model.transformation.FiltersTransformation;
import model.transformation.GreenGreyscaleTransformation;
import model.transformation.ITransformation;
import model.transformation.IntensityGreyscaleTransformation;
import model.transformation.LumaGreyscaleTransformation;
import model.transformation.RedGreyscaleTransformation;
import model.transformation.ValueGreyscaleTransformation;

/**
 * View class represents the GUI of an image processing application.
 * It is an implementation of the IView interface and uses the ActionListener
 * interface to perform actions based on user interaction.
 * It extends JFrame to create the GUI window.
 */
public class View extends JFrame implements ActionListener, IView {
  private final IImageDataBase model;
  private final JButton loadButton;
  private final JButton saveButton;
  private final JButton blurButton;
  private final JPanel brightenPanel;
  private final JPanel blurPanel;
  private final JTextField blurImage;
  private final JTextField blurredImage;
  private final JPanel sharpenPanel;
  private final JButton sharpenButton;
  private final JTextField sharpenImage;
  private final JTextField sharpenedImage;
  private final JPanel sepiaTonePanel;
  private final JButton sepiaToneButton;
  private final JTextField sepiaToneImage;
  private final JTextField sepiaTonedImage;
  private final JButton brightenButton;
  private final JLabel showText;
  private final JTextField brightenValueInput;
  private final JTextField brightenImage;
  private final JTextField brightenedImage;
  private final JTextField componentGreyscaleImage;
  private final JTextField componentGreyscaledImage;
  private final JTextField filterGreyscaleImage;
  private final JTextField filterGreyscaledImage;
  private final JPanel operationPanel;
  private final JPanel loadSavePanel;
  private final List<ViewListener> listenerToNotify;
  private final Canvas canvas;
  private final HistogramPanel histogram;
  private BufferedImage image;

  /**
   * The View constructor initializes the GUI of the application.
   *
   * @param model the IImageDataBase model that the view will interact with
   */
  public View(IImageDataBase model) {
    super("Image Processing Application");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.model = Objects.requireNonNull(model);
    setSize(800, 600);
    setLayout(new BorderLayout());

    this.listenerToNotify = new ArrayList<>();

    this.canvas = new Canvas();
    canvas.setPreferredSize(new Dimension(500, 400));
    JScrollPane scrollImagePanel = new JScrollPane(canvas);
    add(scrollImagePanel, BorderLayout.EAST);

    this.histogram = new HistogramPanel();
    histogram.setPreferredSize(new Dimension(800, 150));
    add(histogram, BorderLayout.SOUTH);

    this.showText = new JLabel("Display text.");

    this.loadSavePanel = new JPanel();
    loadSavePanel.setPreferredSize(new Dimension(800, 50));
    this.loadButton = new JButton("Load Image");
    this.saveButton = new JButton("Save Image");
    loadSavePanel.add(loadButton);
    loadSavePanel.add(saveButton);
    add(loadSavePanel, BorderLayout.NORTH);


    this.brightenButton = new JButton("Brighten Image");
    this.blurButton = new JButton("Blur Image");
    this.sharpenButton = new JButton("Sharpen Image");
    this.sepiaToneButton = new JButton("Sepia Tone Filter");


    this.brightenValueInput = new JTextField("", 3);
    this.brightenImage = new JTextField("", 3);
    this.brightenedImage = new JTextField("", 3);
    this.brightenPanel = new JPanel();
    brightenPanel.add(new JLabel("Brighten Value:"));
    brightenPanel.add(brightenValueInput);
    brightenPanel.add(new JLabel("Image ID:"));
    brightenPanel.add(brightenImage);
    brightenPanel.add(new JLabel("New Image ID:"));
    brightenPanel.add(brightenedImage);
    brightenPanel.add(brightenButton);

    this.blurImage = new JTextField("", 3);
    this.blurredImage = new JTextField("", 3);
    this.blurPanel = new JPanel();
    blurPanel.add(new JLabel("Image ID:"));
    blurPanel.add(blurImage);
    blurPanel.add(new JLabel("New Image ID:"));
    blurPanel.add(blurredImage);
    blurPanel.add(blurButton);

    this.sharpenImage = new JTextField("", 3);
    this.sharpenedImage = new JTextField("", 3);
    this.sharpenPanel = new JPanel();
    sharpenPanel.add(new JLabel("Image ID:"));
    sharpenPanel.add(sharpenImage);
    sharpenPanel.add(new JLabel("New Image ID:"));
    sharpenPanel.add(sharpenedImage);
    sharpenPanel.add(sharpenButton);

    this.sepiaToneImage = new JTextField("", 3);
    this.sepiaTonedImage = new JTextField("", 3);
    this.sepiaTonePanel = new JPanel();
    sepiaTonePanel.add(new JLabel("Image ID:"));
    sepiaTonePanel.add(sepiaToneImage);
    sepiaTonePanel.add(new JLabel("New Image ID:"));
    sepiaTonePanel.add(sepiaTonedImage);
    sepiaTonePanel.add(sepiaToneButton);

    JPanel greyscaleComponentPanel = new JPanel();
    this.componentGreyscaleImage = new JTextField("", 3);
    greyscaleComponentPanel.add(new JLabel("Image ID:"));
    greyscaleComponentPanel.add(componentGreyscaleImage);
    this.componentGreyscaledImage = new JTextField("", 3);
    greyscaleComponentPanel.add(new JLabel("New Image ID:"));
    greyscaleComponentPanel.add(componentGreyscaledImage);

    JRadioButton redComponentButton = new JRadioButton("Red");
    JRadioButton greenComponentButton = new JRadioButton("Green");
    JRadioButton blueComponentButton = new JRadioButton("Blue");
    JRadioButton valueComponentButton = new JRadioButton("Value");
    JRadioButton lumaComponentButton = new JRadioButton("Luma");
    JRadioButton intensityComponentButton = new JRadioButton("Intensity");

    ButtonGroup componentGroup = new ButtonGroup();
    componentGroup.add(redComponentButton);
    componentGroup.add(greenComponentButton);
    componentGroup.add(blueComponentButton);
    componentGroup.add(valueComponentButton);
    componentGroup.add(lumaComponentButton);
    componentGroup.add(intensityComponentButton);

    greyscaleComponentPanel.add(redComponentButton);
    greyscaleComponentPanel.add(greenComponentButton);
    greyscaleComponentPanel.add(blueComponentButton);
    greyscaleComponentPanel.add(valueComponentButton);
    greyscaleComponentPanel.add(lumaComponentButton);
    greyscaleComponentPanel.add(intensityComponentButton);

    JButton greyscaleComponentButton = new JButton("Component Greyscale");
    greyscaleComponentPanel.add(greyscaleComponentButton);

    JPanel greyscaleFilterPanel = new JPanel();
    this.filterGreyscaleImage = new JTextField("", 3);
    greyscaleFilterPanel.add(new JLabel("Image ID:"));
    greyscaleFilterPanel.add(filterGreyscaleImage);
    this.filterGreyscaledImage = new JTextField("", 3);
    greyscaleFilterPanel.add(new JLabel("New Image ID:"));
    greyscaleFilterPanel.add(filterGreyscaledImage);

    JRadioButton redFilterButton = new JRadioButton("Red");
    JRadioButton greenFilterButton = new JRadioButton("Green");
    JRadioButton blueFilterButton = new JRadioButton("Blue");
    JRadioButton lumaFilterButton = new JRadioButton("Luma");
    JRadioButton intensityFilterButton = new JRadioButton("Intensity");

    ButtonGroup filterGroup = new ButtonGroup();
    filterGroup.add(redFilterButton);
    filterGroup.add(greenFilterButton);
    filterGroup.add(blueFilterButton);
    filterGroup.add(lumaFilterButton);
    filterGroup.add(intensityFilterButton);

    greyscaleFilterPanel.add(redFilterButton);
    greyscaleFilterPanel.add(greenFilterButton);
    greyscaleFilterPanel.add(blueFilterButton);
    greyscaleFilterPanel.add(lumaFilterButton);
    greyscaleFilterPanel.add(intensityFilterButton);

    JButton greyscaleFilterButton = new JButton("Greyscale Filter");
    greyscaleFilterPanel.add(greyscaleFilterButton);


    this.loadButton.setActionCommand("load");
    this.saveButton.setActionCommand("save");
    this.brightenButton.setActionCommand("brighten");
    this.blurButton.setActionCommand("blur");
    this.sharpenButton.setActionCommand("sharpen");
    this.sepiaToneButton.setActionCommand("sepia-tone");
    redComponentButton.setActionCommand("red-component");
    greenComponentButton.setActionCommand("green-component");
    blueComponentButton.setActionCommand("blue-component");
    valueComponentButton.setActionCommand("value-component");
    lumaComponentButton.setActionCommand("luma-component");
    intensityComponentButton.setActionCommand("intensity-component");
    redFilterButton.setActionCommand("red-filter");
    greenFilterButton.setActionCommand("green-filter");
    blueFilterButton.setActionCommand("blue-filter");
    lumaFilterButton.setActionCommand("luma-filter");
    intensityFilterButton.setActionCommand("intensity-filter");

    this.loadButton.addActionListener(this);
    this.saveButton.addActionListener(this);
    this.brightenButton.addActionListener(this);
    this.blurButton.addActionListener(this);
    this.sharpenButton.addActionListener(this);
    this.sepiaToneButton.addActionListener(this);
    redComponentButton.addActionListener(this);
    greenComponentButton.addActionListener(this);
    blueComponentButton.addActionListener(this);
    valueComponentButton.addActionListener(this);
    lumaComponentButton.addActionListener(this);
    intensityComponentButton.addActionListener(this);
    redFilterButton.addActionListener(this);
    greenFilterButton.addActionListener(this);
    blueFilterButton.addActionListener(this);
    lumaFilterButton.addActionListener(this);
    intensityFilterButton.addActionListener(this);

    this.operationPanel = new JPanel();
    operationPanel.setPreferredSize(new Dimension(300, 400));

    operationPanel.setLayout(new GridLayout(0, 1, 1, 1));
    operationPanel.add(brightenPanel);
    operationPanel.add(blurPanel);
    operationPanel.add(sharpenPanel);
    operationPanel.add(sepiaTonePanel);
    operationPanel.add(greyscaleComponentPanel);
    operationPanel.add(greyscaleFilterPanel);

    JScrollPane scrollPanel = new JScrollPane(operationPanel);

    add(loadSavePanel, BorderLayout.NORTH);
    add(scrollPanel, BorderLayout.CENTER);

    this.setFocusable(true);
  }

  /**
   * Emits an event to load an image from the filesystem.
   */
  private void emitLoadEvent() {
    for (ViewListener listener : listenerToNotify) {
      final JFileChooser fChooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "PPM, PNG, JPG", "ppm", "png", "jpg");
      fChooser.setFileFilter(filter);
      int returnValue = fChooser.showOpenDialog(View.this);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        File f = fChooser.getSelectedFile();
        IImageState loadedImage = listener.handleLoadEvent(f.getAbsolutePath());
        String destId = JOptionPane.showInputDialog(View.this,
                "Enter ID for the image:");
        this.model.add(destId, loadedImage);

        int[][] histogram = calculateHistogram(loadedImage);
        this.histogram.setHistogram(histogram);
      }
    }
  }

  /**
   * Emits an event to save the current image to the filesystem.
   */
  private void emitSaveEvent() {
    for (ViewListener listener : listenerToNotify) {
      String imageId = JOptionPane.showInputDialog(View.this,
              "Enter ID of the image to save:");

      IImageState imageToSave = model.get(imageId);

      if (imageToSave != null) {
        // User chooses the save location
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        fileChooser.setFileFilter(new FileNameExtensionFilter("PPM, PNG, JPG",
                "ppm", "png", "jpg"));
        int userSelection = fileChooser.showSaveDialog(View.this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
          File fileToSave = fileChooser.getSelectedFile();
          listener.handleSaveEvent(imageToSave, fileToSave.getAbsolutePath());
        }
        renderImage(convertToBufferedImage(imageToSave));

        int[][] histogram = calculateHistogram(imageToSave);
        this.histogram.setHistogram(histogram);
      } else {
        JOptionPane.showMessageDialog(View.this,
                "No image with the given ID was found.");
      }
    }
  }

  /**
   * Emits an event to brighten the current image.
   */
  private void emitBrightenEvent() {
    int brightenValue = Integer.parseInt(brightenValueInput.getText());
    String imageId = brightenImage.getText();
    String destId = brightenedImage.getText();

    IImageState imageToBrighten = model.get(imageId);
    if (imageToBrighten != null) {
      ITransformation brightnessTransformation = new BrightnessTransformation(brightenValue);
      IImageState brightenedImage = brightnessTransformation.run(imageToBrighten);
      this.model.add(destId, brightenedImage);
      renderImage(convertToBufferedImage(brightenedImage));

      int[][] histogram = calculateHistogram(brightenedImage);
      this.histogram.setHistogram(histogram);
    } else {
      JOptionPane.showMessageDialog(View.this,
              "No image with the given ID was found.");
    }
  }

  /**
   * Emits an event to blur the current image.
   */
  private void emitBlurEvent() {
    String imageId = blurImage.getText();
    String destId = blurredImage.getText();

    double[][] blurMatrix = {
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16}
    };

    IImageState imageToBlur = model.get(imageId);

    if (imageToBlur != null) {
      ITransformation blurTransformation = new FiltersTransformation(blurMatrix);
      IImageState blurredImage = blurTransformation.run(imageToBlur);
      this.model.add(destId, blurredImage);
      renderImage(convertToBufferedImage(blurredImage));

      int[][] histogram = calculateHistogram(blurredImage);
      this.histogram.setHistogram(histogram);
    } else {
      JOptionPane.showMessageDialog(View.this,
              "No image with the given ID was found.");
    }
  }

  /**
   * Emits an event to sharpen the current image.
   */
  private void emitSharpenEvent() {
    String imageId = sharpenImage.getText();
    String destId = sharpenedImage.getText();

    double[][] sharpenMatrix = {
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
    };

    IImageState imageToSharpen = model.get(imageId);

    if (imageToSharpen != null) {
      ITransformation sharpenTransformation = new FiltersTransformation(sharpenMatrix);
      IImageState sharpenedImage = sharpenTransformation.run(imageToSharpen);
      this.model.add(destId, sharpenedImage);
      renderImage(convertToBufferedImage(sharpenedImage));

      int[][] histogram = calculateHistogram(sharpenedImage);
      this.histogram.setHistogram(histogram);
    } else {
      JOptionPane.showMessageDialog(View.this,
              "No image with the given ID was found.");
    }
  }

  /**
   * Emits an event to apply a sepia tone to the current image.
   */
  private void emitSepiaToneEvent() {
    String imageId = sepiaToneImage.getText();
    String destId = sepiaTonedImage.getText();

    double[][] sepiaToneGreyscaleMatrix = {
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}
    };

    IImageState imageToSepiaTone = model.get(imageId);

    if (imageToSepiaTone != null) {
      ITransformation greyscaleFilterTransformation =
              new ColorTransformationsFilter(sepiaToneGreyscaleMatrix);
      IImageState filteredImage = greyscaleFilterTransformation.run(imageToSepiaTone);
      this.model.add(destId, filteredImage);
      renderImage(convertToBufferedImage(filteredImage));

      int[][] histogram = calculateHistogram(filteredImage);
      this.histogram.setHistogram(histogram);
    } else {
      JOptionPane.showMessageDialog(View.this,
              "No image with the given ID was found.");
    }
  }

  /**
   * Emits an event to apply a red component transformation to the current image.
   */
  private void emitRedComponentEvent() {
    String imageId = componentGreyscaleImage.getText();
    String destId = componentGreyscaledImage.getText();

    IImageState imageToRedComponent = model.get(imageId);

    if (imageToRedComponent != null) {
      ITransformation redGreyscaleTransformation = new RedGreyscaleTransformation();
      IImageState redGreyedImage = redGreyscaleTransformation.run(imageToRedComponent);
      this.model.add(destId, redGreyedImage);
      renderImage(convertToBufferedImage(redGreyedImage));

      int[][] histogram = calculateHistogram(redGreyedImage);
      this.histogram.setHistogram(histogram);
    } else {
      JOptionPane.showMessageDialog(View.this,
              "No image with the given ID was found.");
    }
  }

  /**
   * Emits an event to apply a green component transformation to the current image.
   */
  private void emitGreenComponentEvent() {
    String imageId = componentGreyscaleImage.getText();
    String destId = componentGreyscaledImage.getText();

    IImageState imageToGreenComponent = model.get(imageId);

    if (imageToGreenComponent != null) {
      ITransformation greenGreyscaleTransformation = new GreenGreyscaleTransformation();
      IImageState greenGreyedImage = greenGreyscaleTransformation.run(imageToGreenComponent);
      this.model.add(destId, greenGreyedImage);
      renderImage(convertToBufferedImage(greenGreyedImage));

      int[][] histogram = calculateHistogram(greenGreyedImage);
      this.histogram.setHistogram(histogram);
    } else {
      JOptionPane.showMessageDialog(View.this,
              "No image with the given ID was found.");
    }
  }

  /**
   * Emits an event to apply a blue component transformation to the current image.
   */
  private void emitBlueComponentEvent() {
    String imageId = componentGreyscaleImage.getText();
    String destId = componentGreyscaledImage.getText();

    IImageState imageToBlueComponent = model.get(imageId);

    if (imageToBlueComponent != null) {
      ITransformation blueGreyscaleTransformation = new BlueGreyscaleTransformation();
      IImageState blueGreyedImage = blueGreyscaleTransformation.run(imageToBlueComponent);
      this.model.add(destId, blueGreyedImage);
      renderImage(convertToBufferedImage(blueGreyedImage));

      int[][] histogram = calculateHistogram(blueGreyedImage);
      this.histogram.setHistogram(histogram);
    } else {
      JOptionPane.showMessageDialog(View.this,
              "No image with the given ID was found.");
    }
  }

  /**
   * Emits an event to apply a value component transformation to the current image.
   */
  private void emitValueComponentEvent() {
    String imageId = componentGreyscaleImage.getText();
    String destId = componentGreyscaledImage.getText();

    IImageState imageToValueComponent = model.get(imageId);

    if (imageToValueComponent != null) {
      ITransformation valueGreyscaleTransformation = new ValueGreyscaleTransformation();
      IImageState valueGreyedImage = valueGreyscaleTransformation.run(imageToValueComponent);
      this.model.add(destId, valueGreyedImage);
      renderImage(convertToBufferedImage(valueGreyedImage));

      int[][] histogram = calculateHistogram(valueGreyedImage);
      this.histogram.setHistogram(histogram);
    } else {
      JOptionPane.showMessageDialog(View.this,
              "No image with the given ID was found.");
    }
  }

  /**
   * Emits an event to apply a luma component transformation to the current image.
   */
  private void emitLumaComponentEvent() {
    String imageId = componentGreyscaleImage.getText();
    String destId = componentGreyscaledImage.getText();

    IImageState imageToLumaComponent = model.get(imageId);

    if (imageToLumaComponent != null) {
      ITransformation lumaGreyscaleTransformation = new LumaGreyscaleTransformation();
      IImageState lumaGreyedImage = lumaGreyscaleTransformation.run(imageToLumaComponent);
      this.model.add(destId, lumaGreyedImage);
      renderImage(convertToBufferedImage(lumaGreyedImage));

      int[][] histogram = calculateHistogram(lumaGreyedImage);
      this.histogram.setHistogram(histogram);
    } else {
      JOptionPane.showMessageDialog(View.this,
              "No image with the given ID was found.");
    }
  }

  /**
   * Emits an event to apply an intensity component transformation to the current image.
   */
  private void emitIntensityComponentEvent() {
    String imageId = componentGreyscaleImage.getText();
    String destId = componentGreyscaledImage.getText();

    IImageState imageToIntensityComponent = model.get(imageId);

    if (imageToIntensityComponent != null) {
      ITransformation intensityGreyscaleTransformation = new IntensityGreyscaleTransformation();
      IImageState intensityGreyedImage = intensityGreyscaleTransformation.run(imageToIntensityComponent);
      this.model.add(destId, intensityGreyedImage);
      renderImage(convertToBufferedImage(intensityGreyedImage));

      int[][] histogram = calculateHistogram(intensityGreyedImage);
      this.histogram.setHistogram(histogram);
    } else {
      JOptionPane.showMessageDialog(View.this,
              "No image with the given ID was found.");
    }
  }

  /**
   * Emits an event to apply a red filter to the current image.
   */
  private void emitRedFilterEvent() {
    String imageId = filterGreyscaleImage.getText();
    String destId = filterGreyscaledImage.getText();

    double[][] redGreyscaleMatrix = {
            {1, 0, 0},
            {1, 0, 0},
            {1, 0, 0},
    };

    IImageState imageToRedFilter = model.get(imageId);

    if (imageToRedFilter != null) {
      ITransformation greyscaleFilterTransformation =
              new ColorTransformationsFilter(redGreyscaleMatrix);
      IImageState filteredImage = greyscaleFilterTransformation.run(imageToRedFilter);
      this.model.add(destId, filteredImage);
      renderImage(convertToBufferedImage(filteredImage));

      int[][] histogram = calculateHistogram(filteredImage);
      this.histogram.setHistogram(histogram);
    } else {
      JOptionPane.showMessageDialog(View.this,
              "No image with the given ID was found.");
    }
  }

  /**
   * Emits an event to apply a green filter to the current image.
   */
  private void emitGreenFilterEvent() {
    String imageId = filterGreyscaleImage.getText();
    String destId = filterGreyscaledImage.getText();

    double[][] greenGreyscaleMatrix = {
            {0, 1, 0},
            {0, 1, 0},
            {0, 1, 0},
    };

    IImageState imageToGreenFilter = model.get(imageId);

    if (imageToGreenFilter != null) {
      ITransformation greyscaleFilterTransformation =
              new ColorTransformationsFilter(greenGreyscaleMatrix);
      IImageState filteredImage = greyscaleFilterTransformation.run(imageToGreenFilter);
      this.model.add(destId, filteredImage);
      renderImage(convertToBufferedImage(filteredImage));

      int[][] histogram = calculateHistogram(filteredImage);
      this.histogram.setHistogram(histogram);
    } else {
      JOptionPane.showMessageDialog(View.this,
              "No image with the given ID was found.");
    }
  }

  /**
   * Emits an event to apply a blue filter to the current image.
   */
  private void emitBlueFilterEvent() {
    String imageId = filterGreyscaleImage.getText();
    String destId = filterGreyscaledImage.getText();

    double[][] blueGreyscaleMatrix = {
            {0, 0, 1},
            {0, 0, 1},
            {0, 0, 1},
    };

    IImageState imageToBlueFilter = model.get(imageId);

    if (imageToBlueFilter != null) {
      ITransformation greyscaleFilterTransformation =
              new ColorTransformationsFilter(blueGreyscaleMatrix);
      IImageState filteredImage = greyscaleFilterTransformation.run(imageToBlueFilter);
      this.model.add(destId, filteredImage);
      renderImage(convertToBufferedImage(filteredImage));

      int[][] histogram = calculateHistogram(filteredImage);
      this.histogram.setHistogram(histogram);
    } else {
      JOptionPane.showMessageDialog(View.this,
              "No image with the given ID was found.");
    }
  }

  /**
   * Emits an event to apply a luma filter to the current image.
   */
  private void emitLumaFilterEvent() {
    String imageId = filterGreyscaleImage.getText();
    String destId = filterGreyscaledImage.getText();

    double[][] lumaGreyscaleMatrix = {
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
    };

    IImageState imageToLumaFilter = model.get(imageId);

    if (imageToLumaFilter != null) {
      ITransformation greyscaleFilterTransformation =
              new ColorTransformationsFilter(lumaGreyscaleMatrix);
      IImageState filteredImage = greyscaleFilterTransformation.run(imageToLumaFilter);
      this.model.add(destId, filteredImage);
      renderImage(convertToBufferedImage(filteredImage));

      int[][] histogram = calculateHistogram(filteredImage);
      this.histogram.setHistogram(histogram);
    } else {
      JOptionPane.showMessageDialog(View.this,
              "No image with the given ID was found.");
    }
  }

  /**
   * Emits an event to apply an intensity filter to the current image.
   */
  private void emitIntensityFilterEvent() {
    String imageId = filterGreyscaleImage.getText();
    String destId = filterGreyscaledImage.getText();

    double[][] intensityGreyscaleMatrix = {
            {1.0 / 3, 1.0 / 3, 1.0 / 3},
            {1.0 / 3, 1.0 / 3, 1.0 / 3},
            {1.0 / 3, 1.0 / 3, 1.0 / 3},
    };

    IImageState imageToIntensityFilter = model.get(imageId);

    if (imageToIntensityFilter != null) {
      ITransformation greyscaleFilterTransformation =
              new ColorTransformationsFilter(intensityGreyscaleMatrix);
      IImageState filteredImage = greyscaleFilterTransformation.run(imageToIntensityFilter);
      this.model.add(destId, filteredImage);
      renderImage(convertToBufferedImage(filteredImage));

      int[][] histogram = calculateHistogram(filteredImage);
      this.histogram.setHistogram(histogram);
    } else {
      JOptionPane.showMessageDialog(View.this,
              "No image with the given ID was found.");
    }
  }

  /**
   * Handles all action events that occur within the GUI.
   *
   * @param e the ActionEvent triggered by the user
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "load":
        emitLoadEvent();
        break;
      case "save":
        emitSaveEvent();
        break;
      case "brighten":
        emitBrightenEvent();
        break;
      case "blur":
        emitBlurEvent();
        break;
      case "sharpen":
        emitSharpenEvent();
        break;
      case "sepia-tone":
        emitSepiaToneEvent();
        break;
      case "red-component":
        emitRedComponentEvent();
        break;
      case "green-component":
        emitGreenComponentEvent();
        break;
      case "blue-component":
        emitBlueComponentEvent();
        break;
      case "value-component":
        emitValueComponentEvent();
        break;
      case "luma-component":
        emitLumaComponentEvent();
        break;
      case "intensity-component":
        emitIntensityComponentEvent();
        break;
      case "red-filter":
        emitRedFilterEvent();
        break;
      case "green-filter":
        emitGreenFilterEvent();
        break;
      case "blue-filter":
        emitBlueFilterEvent();
        break;
      case "luma-filter":
        emitLumaFilterEvent();
        break;
      case "intensity-filter":
        emitIntensityFilterEvent();
        break;
      default:
        throw new IllegalStateException("Unknown action command.");
    }
  }

  /**
   * Renders the image in the GUI.
   *
   * @param image the BufferedImage to be rendered
   */
  @Override
  public void renderImage(BufferedImage image) {
    this.image = image;
    canvas.renderImage(image);
  }

  /**
   * Adds a listener to the view. The listener will be notified when certain events occur.
   *
   * @param listener the listener to be added
   */
  @Override
  public void addViewListener(ViewListener listener) {
    this.listenerToNotify.add(listener);
  }

  /**
   * Converts the given IImageState to a BufferedImage.
   *
   * @param imageState the IImageState to be converted
   * @return the BufferedImage representation of the given IImageState
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

  /**
   * Calculates the histogram of the given IImageState.
   *
   * @param image the IImageState for which the histogram is to be calculated
   * @return a 2D int array representing the histogram of the given image
   */
  private int[][] calculateHistogram(IImageState image) {
    int[][] histogram = new int[4][256];

    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int red = image.getRedChannel(x, y);
        int green = image.getGreenChannel(x, y);
        int blue = image.getBlueChannel(x, y);
        int intensity = (red + green + blue) / 3;

        histogram[0][red]++;
        histogram[1][green]++;
        histogram[2][blue]++;
        histogram[3][intensity]++;
      }
    }
    return histogram;
  }
}
