import java.io.InputStreamReader;
import java.util.Scanner;

import GUI.controller.GUIController;
import GUI.controller.IGUIController;
import GUI.view.IView;
import GUI.view.View;
import controller.ControllerImpl;
import controller.IController;
import model.IImageDataBase;
import model.ImageDataBase;
import view.StringAppendable;

/**
 * The type Main.
 */
public class Main {

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    IImageDataBase model = new ImageDataBase();
    StringAppendable appendable = new StringAppendable();
    IController controller;

    Scanner scanner = new Scanner(System.in);
    System.out.println("Please enter a command (-file <path-to-script-file>, -text, or <enter> for GUI):");
    String command = scanner.nextLine().trim();

    if (command.isEmpty()) {
      IView view = new View(model);
      IGUIController controller2 = new GUIController(model, view);
      controller2.run();
    } else if (command.startsWith("-file ")) {
      // Execute script file.
      controller = new ControllerImpl(new InputStreamReader(System.in), model, appendable);
      controller.start();
    } else if (command.equals("-text")) {
      // Open in interactive text mode.
      controller = new ControllerImpl(new InputStreamReader(System.in), model, appendable);
      controller.start();
    } else {
      System.out.println("Invalid command. The program accepts the following commands: \n"
              + "-file <path-to-script-file> : Execute a script file. \n"
              + "-text : Open in interactive text mode. \n"
              + "<enter> : Open the graphical user interface.");
    }
  }
}
