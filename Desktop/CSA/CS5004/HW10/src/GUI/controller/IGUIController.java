package GUI.controller;

import GUI.view.ViewListener;

/**
 * Interface for the GUI Controller. Provides methods to handle view events and start the GUI.
 */
public interface IGUIController extends ViewListener {

  /**
   * Starts the GUI.
   */
  void run();
}
