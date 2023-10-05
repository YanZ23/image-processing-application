package controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import controller.command.BlueGreyscaleCommand;
import controller.command.BlueGreyscaleFilterCommand;
import controller.command.BrightnessAdjustCommand;
import controller.command.GreenGreyscaleCommand;
import controller.command.GreenGreyscaleFilterCommand;
import controller.command.IntensityGreyscaleFilterCommand;
import controller.command.LumaGreyscaleFilterCommand;
import controller.command.ICommand;
import controller.command.BlurCommand;
import controller.command.IntensityGreyscaleCommand;
import controller.command.LoadCommand;
import controller.command.LumaGreyscaleCommand;
import controller.command.RedGreyscaleCommand;
import controller.command.RedGreyscaleFilterCommand;
import controller.command.SaveCommand;
import controller.command.SepiaToneFilterCommand;
import controller.command.SharpenCommand;
import controller.command.ValueGreyscaleCommand;
import model.IImageDataBase;
import view.StringAppendable;

/**
 * The implementation of the IController interface that handles user commands
 * and manages image transformations.
 */
public class ControllerImpl implements IController {
  private final Readable input;
  private final IImageDataBase model;
  private final StringAppendable appendable;
  private final Map<String, ICommand> commandMap;

  /**
   * Instantiates a new Controller with the specified input source, image database, and output.
   *
   * @param input      The source of input commands.
   * @param model      The image database to store and retrieve images.
   * @param appendable The output to display processed messages.
   */
  public ControllerImpl(Readable input, IImageDataBase model, StringAppendable appendable) {
    this.input = Objects.requireNonNull(input);
    this.model = Objects.requireNonNull(model);
    this.appendable = Objects.requireNonNull(appendable);

    this.commandMap = new HashMap<String, ICommand>();
    this.commandMap.put("load", new LoadCommand(model));
    this.commandMap.put("save", new SaveCommand());
    this.commandMap.put("brighten", new BrightnessAdjustCommand(model));
    this.commandMap.put("red-component", new RedGreyscaleCommand(model));
    this.commandMap.put("green-component", new GreenGreyscaleCommand(model));
    this.commandMap.put("blue-component", new BlueGreyscaleCommand(model));
    this.commandMap.put("value-component", new ValueGreyscaleCommand(model));
    this.commandMap.put("luma-component", new LumaGreyscaleCommand(model));
    this.commandMap.put("intensity-component", new IntensityGreyscaleCommand(model));
    this.commandMap.put("blur", new BlurCommand(model));
    this.commandMap.put("sharpen", new SharpenCommand(model));
    this.commandMap.put("red-greyscale", new RedGreyscaleFilterCommand(model));
    this.commandMap.put("green-greyscale", new GreenGreyscaleFilterCommand(model));
    this.commandMap.put("blue-greyscale", new BlueGreyscaleFilterCommand(model));
    this.commandMap.put("luma-greyscale", new LumaGreyscaleFilterCommand(model));
    this.commandMap.put("intensity-greyscale", new IntensityGreyscaleFilterCommand(model));
    this.commandMap.put("sepia-tone", new SepiaToneFilterCommand(model));
    this.commandMap.put("-file", (scanner, ignored) -> processScriptFile(scanner.next(), model));
  }

  /**
   * Writes the specified message to the output appendable.
   *
   * @param message The message to be written to the output.
   */
  private void write(String message) {
    try {
      this.appendable.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("Writing to the appendable failed.");
    }
  }

  /**
   * Process the commands from the script file.
   *
   * @param fileName The name of the script file.
   * @param model    The image database to store and retrieve images.
   */
  private void processScriptFile(String fileName, IImageDataBase model) {
    try {
      Scanner fileScanner = new Scanner(new FileReader(fileName));
      while (fileScanner.hasNext()) {
        String command = fileScanner.next();
        ICommand commandToRun = this.commandMap.getOrDefault(command, null);
        if (commandToRun == null) {
          write("Invalid command in the script file: " + command);
          continue;
        }
        if (commandToRun instanceof SaveCommand) {
          ((SaveCommand) commandToRun).setOutput(appendable);
        }
        if (commandToRun instanceof LoadCommand) {
          ((LoadCommand) commandToRun).setOutput(appendable);
        }

        try {
          commandToRun.run(fileScanner, model);
        } catch (IllegalStateException e) {
          write(e.getMessage());
        }
      }
      fileScanner.close();
    } catch (FileNotFoundException e) {
      write("File not found: " + fileName);
    }
  }

  @Override
  public void start() {
    Scanner scanner = new Scanner(this.input);

    while (scanner.hasNext()) {
      String command = scanner.next();

      ICommand commandToRun = this.commandMap.getOrDefault(command, null);
      if (commandToRun == null) {
        write("Invalid command.");
        continue;
      }

      if (commandToRun instanceof SaveCommand) {
        ((SaveCommand) commandToRun).setOutput(appendable);
      }
      if (commandToRun instanceof LoadCommand) {
        ((LoadCommand) commandToRun).setOutput(appendable);
      }

      try {
        commandToRun.run(scanner, model);
      } catch (IllegalStateException e) {
        write(e.getMessage());
      }
    }
  }
}
